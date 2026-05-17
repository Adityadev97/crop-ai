document.addEventListener('DOMContentLoaded', () => {

    // --- Navigation Logic ---
    const navItems = document.querySelectorAll('.nav-links li');
    const sections = document.querySelectorAll('.view-section');

    navItems.forEach(item => {
        item.addEventListener('click', () => {
            // Remove active class from all
            navItems.forEach(nav => nav.classList.remove('active'));
            sections.forEach(sec => sec.classList.remove('active-view'));

            // Add active class to clicked
            item.classList.add('active');
            const targetId = item.getAttribute('data-target');
            document.getElementById(targetId).classList.add('active-view');
            
            // Adjust header title based on view
            const titles = {
                'dashboard': 'Farmer Dashboard',
                'scanner': 'AI Crop Scanner',
                'chatbot': 'AgriBot Assistant',
                'weather': 'Weather Forecast & Alerts',
                'market': 'Market Prices'
            };
            document.getElementById('page-title').innerText = titles[targetId] || 'ProCrop';
        });
    });

    // --- AI Scanner Logic ---
    const uploadArea = document.getElementById('uploadArea');
    const imageInput = document.getElementById('imageInput');
    const scanResult = document.getElementById('scanResult');
    const loadingOverlay = document.getElementById('loadingOverlay');
    const resetScanBtn = document.getElementById('resetScanBtn');

    uploadArea.addEventListener('click', () => imageInput.click());

    uploadArea.addEventListener('dragover', (e) => {
        e.preventDefault();
        uploadArea.classList.add('dragover');
    });

    uploadArea.addEventListener('dragleave', () => {
        uploadArea.classList.remove('dragover');
    });

    uploadArea.addEventListener('drop', (e) => {
        e.preventDefault();
        uploadArea.classList.remove('dragover');
        if (e.dataTransfer.files.length) {
            handleImageUpload(e.dataTransfer.files[0]);
        }
    });

    imageInput.addEventListener('change', (e) => {
        if (e.target.files.length) {
            handleImageUpload(e.target.files[0]);
        }
    });

    function handleImageUpload(file) {
        if (!file.type.startsWith('image/')) {
            alert('Please select an image file.');
            return;
        }

        // Show loading
        loadingOverlay.classList.remove('hidden');

        // Create FormData
        const formData = new FormData();
        formData.append('image', file);

        // Fetch API to our Spring Boot Backend
        fetch('http://localhost:8080/api/ai/scan-disease', {
            method: 'POST',
            body: formData
        })
        .then(response => {
            if (!response.ok) throw new Error('Network response was not ok');
            return response.json();
        })
        .then(data => {
            // Hide loading overlay
            loadingOverlay.classList.add('hidden');
            uploadArea.classList.add('hidden');
            
            // Populate result
            document.getElementById('diseaseName').innerText = data.disease;
            document.getElementById('confidenceScore').innerText = data.confidence.toFixed(1);
            document.getElementById('recommendationText').innerText = data.recommendation;
            
            // Update styles based on result
            if (data.disease === 'Healthy') {
                document.getElementById('diseaseName').className = 'text-primary';
                document.querySelector('.result-header i').style.color = 'var(--success)';
            } else {
                document.getElementById('diseaseName').className = 'warning';
                document.querySelector('.result-header i').style.color = 'var(--danger)';
            }

            scanResult.classList.remove('hidden');
        })
        .catch(error => {
            console.error('Error scanning image:', error);
            loadingOverlay.classList.add('hidden');
            alert('Failed to scan image. Is the backend running?');
        });
    }

    resetScanBtn.addEventListener('click', () => {
        scanResult.classList.add('hidden');
        uploadArea.classList.remove('hidden');
        imageInput.value = '';
    });

    // --- Chatbot Logic ---
    const chatInput = document.getElementById('chatInput');
    const sendMsgBtn = document.getElementById('sendMsgBtn');
    const chatHistory = document.getElementById('chatHistory');
    const voiceBtn = document.getElementById('voiceBtn');

    function appendMessage(text, sender) {
        const msgDiv = document.createElement('div');
        msgDiv.className = `chat-message ${sender}`;
        
        let contentHtml = '';
        if (sender === 'bot') {
            contentHtml = `<div class="avatar"><i class="fa-solid fa-robot"></i></div>`;
        }
        
        contentHtml += `<div class="message-content">${text}</div>`;
        msgDiv.innerHTML = contentHtml;
        
        chatHistory.appendChild(msgDiv);
        chatHistory.scrollTop = chatHistory.scrollHeight;
    }

    function sendMessage() {
        const text = chatInput.value.trim();
        if (!text) return;

        // Display user message
        appendMessage(text, 'user');
        chatInput.value = '';

        // Call Backend API
        fetch('http://localhost:8080/api/ai/chatbot', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ message: text })
        })
        .then(res => res.json())
        .then(data => {
            setTimeout(() => {
                appendMessage(data.reply, 'bot');
            }, 500); // Slight delay for realism
        })
        .catch(error => {
            console.error('Chat error:', error);
            appendMessage("Sorry, I am having trouble connecting to the server.", 'bot');
        });
    }

    sendMsgBtn.addEventListener('click', sendMessage);
    chatInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') sendMessage();
    });

    // Voice to Text Mocking (Web Speech API if available)
    if ('webkitSpeechRecognition' in window) {
        const recognition = new webkitSpeechRecognition();
        recognition.lang = 'hi-IN'; // Default to Hindi
        recognition.continuous = false;
        
        voiceBtn.addEventListener('click', () => {
            voiceBtn.classList.add('recording');
            voiceBtn.style.color = "red";
            recognition.start();
        });

        recognition.onresult = function(event) {
            const transcript = event.results[0][0].transcript;
            chatInput.value = transcript;
            voiceBtn.style.color = "var(--text-muted)";
            sendMessage();
        };

        recognition.onerror = function(event) {
            console.error("Speech error:", event.error);
            voiceBtn.style.color = "var(--text-muted)";
        };
    } else {
        voiceBtn.style.display = "none";
    }

});
