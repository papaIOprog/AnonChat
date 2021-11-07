export default (ctx, inject) => {
  ctx.userNameGenerator = new UserName();
  inject('userNameGenerator', ctx.userNameGenerator)
}


export class UserName {

  constructor() {
    this.firstName = ["Fat", "Unadvised", "Able", "Obscene", "Overrated", "Longing", "Smooth", "Elfin", "Kindhearted", "Whimsical", "Bouncy", "Chilly", "Financial", "Violent", "Fascinated", "Substantial", "Gratis", "Magenta", "Strange", "Wanting", "Deafening", "Evanescent", "Youthful", "Roomy", "Narrow", "Gleaming", "Parsimonious", "Supreme", "Ceaseless", "Soggy", "Unsightly", "Educational", "Tawdry", "Chief", "Few", "Receptive", "Tasteless", "Competitive", "Whispering", "Needless", "Useful", "Teeny-tiny", "Penitent", "Enormous", "Awesome", "Psychedelic", "Symptomatic", "Technical", "Tacit", "Breezy", "Lopsided", "Gamy", "Lavish", "Four", "Scintillating", "Furry", "Left", "Offbeat", "Equal", "Functional", "Capricious", "Scared", "Electrical", "Informal", "Obsequious", "Squeamish", "Dull", "Yummy", "Closed", "Feeble", "Immediate", "Energetic", "Hateful", "Uninterested", "Versed", "Instinctive", "Material", "Combative", "Fallacious", "Probable", "Courageous", "Dark", "Lumpy", "Fragile", "Available", "Workable", "Attractive", "Bored", "Real", "Tense", "Public", "Hungry", "Free", "Tidy", "Marvelous", "Juvenile", "Different", "Painstaking", "Long", "Short"]
    this.secondName = ["Employer", "Meat", "Youth", "Election", "Departure", "Entry", "Mall", "Bath", "Advice", "Football", "Ratio", "Marketing", "Power", "Error", "Hospital", "Client", "Cabinet", "Courage", "Diamond", "Mood", "Audience", "Finding", "Height", "Teaching", "Song", "Road", "Music", "Presence", "Loss", "Heart", "Poet", "Media", "Analysis", "Camera", "Fishing", "Reception", "Emotion", "Passion", "Math", "Girl", "Mixture", "Ear", "Hat", "Inflation", "City", "Instance", "Driver", "Inspector", "Quantity", "Garbage", "Village", "Student", "College", "Sister", "Concept", "Equipment", "Cookie", "Community", "Control", "Series", "County", "Nation", "Bathroom", "Intention", "Accident", "Health", "Revenue", "Recipe", "Grocery", "Tennis", "Gate", "Reality", "Response", "Context", "Secretary", "Director", "Painting", "Chapter", "Sir", "Confusion", "Passenger", "Month", "Professor", "Success", "Throat", "Baseball", "Teacher", "Artisan", "Movie", "Breath", "Meal", "Ladder", "Stranger", "Aspect", "Studio", "Customer", "Disk", "Woman", "Poetry", "Truth"]
    this.hash = Array.from(Array(this.firstName.length * this.secondName.length).keys());
    this.pointer = 0;
    this.names = new Map();
    for (let i = this.hash.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      const temp = this.hash[i];
      this.hash[i] = this.hash[j];
      this.hash[j] = temp;
    }
  }

  getName(hashName) {
    const name = this.names.get(hashName);
    if (name !== undefined && name !== "") {
      return name;
    } else {
      const hash = this.hash[this.pointer];
      this.pointer = (this.pointer + 1) % (this.firstName.length * this.secondName.length);
      const name = this.firstName[Math.floor(hash / this.firstName.length)] + " " + this.secondName[hash % this.secondName.length];
      this.names.set(hashName, name);
      return name;
    }
  }
}
