const SpeechRecognition = window.SpeechRecognition || webkitSpeechRecognition;
const SpeechSynthesis = window.speechSynthesis || webkitSpeechSynthesis;

const Speech = {
	recognition: null,

	onHear(heard) {console.log("Heard: " + heard);},

	onRecognize(recognizeResponse) {console.log("Recognized: " + recognizeResponse);},

	initSpeechRecognition(onHearHandler, onRecognizeHandler) {
		if (SpeechRecognition) {
			recognition = new SpeechRecognition();
			this.onHear = onHearHandler ? onHearHandler : this.onHear;
			this.onRecognize = onRecognizeHandler ? onRecognizeHandler : this.onRecognize;
		
			recognition.addEventListener('result', (event) => {
				const heard = event.results[event.resultIndex][0].transcript;
				const confidence = event.results[event.resultIndex][0].confidence;
				this.postText(heard, confidence);
				this.onHear(heard);
			});
		}
	},
	
	listen() { recognition.start(); },

	stopListening() { recognition.stop(); },

	postText(text, confidence) {
		let data = {text: text, confidence: confidence};

		fetch("/nlu", {
			method: "POST",
			headers: {'Content-Type': 'application/json'},
			body: JSON.stringify(data)
		})
		.then(res => res.json())
		.then(this.onRecognize);
	},

	speak(text, voice) {
		if (SpeechSynthesis) {
			const utterance = new SpeechSynthesisUtterance(text);
			if (voice) {
				utterance.voice = voice;
				utterance.lang = voice.lang;
				utterance.voiceURI = voice.voiceURI;
				utterance.pitch = 1;
				utterance.volume = 1;
			}
			SpeechSynthesis.speak(utterance);
		}
	},
	
	getVoiceByName(voiceName) {
		const voices = window.speechSynthesis.getVoices();
		const voiceMatch =  voices
			.filter(voice => voice.name === voiceName);
		if (voiceMatch && voiceMatch.length > 0) {
			console.log(voiceMatch[0].name);
			return voiceMatch[0];
		}
		return null;
	},
	
	getVoicesByLanguage(language) {
		return window.speechSynthesis.getVoices().filter(voice => voice.lang === language);
	}
};

