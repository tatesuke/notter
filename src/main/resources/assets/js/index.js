(function(notterApp) {
	
	notterApp.controller("indexController", ["noteServie", function(noteServie){
		
		this.newFormDisable = false;
		this.newText = "";
		this.newTags = "";
		this.notes = [];
		
		this.constructor = function() {
			let that = this;
			noteServie.findAll().then(function(response) {
				that.notes = response.data;
			});
		}
		
		this.onKeyDown = function(e) {
			if (!e.ctrlKey) {
				return;
			}
			if (e.key.toLowerCase() != "enter") {
				return;
			}
			this.add();
		}
		
		this.add = function() {
			this.newFormDisable = true;
		
			let that = this;
			noteServie.add(this.newText, this.newTags).then(function(response) {
				that.notes.push(response.data[0]);
				that._updateNotes(response.data.slice(1));
				that.newFormDisable = false;
				that.newText = "";
				that.newTags = "";
			}, function() {
				alert("エラー");
				that.newFormDisable = false;
			});
		}
		
		this.remove = function(note) {
			let confirm = window.confirm("削除すると会話も追えなくなります。本当に削除しますか？");
			
			if (confirm == false) {
				return;
			}
			
			let that = this;
			noteServie.remove(note).then(function(response) {
				note.isDeleted = true;
				that._updateNotes(response.data);
			});
		}
		
		this.replyTo = function(note) {
			this.newText = this.newText + "[" + note.id + "]";
		}
		
		this.hasTag = function (note, tag) {
			if (!note.tags) {
				return false;
			}
			for (let i = 0; i < note.tags.length; i++) {
				if (note.tags[i].text == tag) {
					return true;
				}
			} 
			return false;
		}
		
		this.addTag = function(note) {
			let input = window.prompt("タグを入力してください。空白で区切れば複数登録できます。");
			if (input == null || input.trim() == "") {
				return;
			}
			
			let texts = [input];
			
			for (let i = 0; i < texts.length; i++) {
				let text = texts[i];
				if (this.hasTag(note, text)) {
					continue;
				}
				note.tags.push({text:text});
			}
			
			let that = this;
			noteServie.update(note).then(function(response) {
				that._updateNotes(response.data);
			}, function() {
				alert("エラー");
			});
		}
		
		this.toggleTag = function (note, tag) {
			let that = this;
			noteServie.toggleTag(note, tag).then(function(response) {
				that._updateNotes(response.data);
			}, function() {
				alert("エラー");
			});
		}
		
		this.noteFilter = function(note) {
			return !note.isDeleted;
		}
		
		this._updateNotes = function(notes) {
			for (let i = 0; i < notes.length; i++) {
				let note = notes[i];
				let index = this._findIndex(note);
				if (index == -1) {
					continue;
				}
				this.notes.splice(index, 1, note);
			}
		} 
		
		this._findIndex = function(note) {
			// そのうち2分探索に直したい
			for (let i = 0; i < this.notes.length; i++) {
				if (this.notes[i].id == note.id) {
					return i;
				}
			}
			return -1;
		}
		
		this.constructor();
		
	}]);
	
})(com.tatesuke.notter.notterApp);