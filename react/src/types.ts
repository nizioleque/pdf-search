export interface Document {
  id: string;
  title: string;
  author: string;
}

export interface Occurrences {
  [key: string]: number[];
}
