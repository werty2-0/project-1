
/**
 * This class models a song that has a title, artist, genre, etc.
 */
public class Song implements SongInterface {
	private String title;	
	private String artist;
	private String genre;
	private int year;
	private int bpm;
	private int energy;
	private int danceability;
	private int loudness;
	private int liveness;
	

	/**
	 * Constructs a song using a row from "songs.csv"
	 */
	public Song(String row){

                // split song row into each section
                String[] information = row.split(",");

                // create array of final data from the song row
                String[] result  = new String[9];

                // j will keep track of the index in the information array
                int j = 0;

                // loop through 9 times to get 9 data points within our final result
                for(int i = 0; i < result.length;i++) {
                        // if our data point has no quotation at the start, it is not a special case so we can directly put it in the final result array
                        if(information[j].charAt(0) != '\"') {
                                result[i] = information[j];
                                j++;
                        }

                        // else, we have to concatenate elements wrongly splitted by commas and/or replace double quotes with a single quote
                        else {

                                // concat will hold final concatenated data point to put into result array
                                String concat = "";

                                // start and back of data points in the information array to concatenate together
                                int start = i;
                                int back = i;

                                // find amount of quotes in element
                                //Needed to know so that we know when we have to concatenate with other elements to create full data point
                                int quoteCount = 0;
                                for(int b = information[start].length() - 1;b >= 0; b--) {
                                        if(information[start].charAt(b) == '\"') quoteCount++;
                                }

                                // if their are an odd amount of quotes in the element, keep advancing back pointer till we find the element with the ending quote
                                if(quoteCount % 2 == 1) {
                                        back++;
                                        while(information[back].charAt(information[back].length() - 1) !=  '\"') {
                                                back++;
                                        }
                                }

                                // concatnate multiple information spots together if we need to
                                if(start != back) {
                                        // goes from start to back index in the information array to concatenate the elements that were wrongly splitted together
                                        for(int z = start; z <= back;z++) {
                                        // makes sure to get rid of single quotes at the start and end of the elements and add commas when concatenating
                                                if(z == start) concat += information[z].substring(1);
                                                else if(z == back) concat += information[z].substring(0, information[z].length() - 1);
                                                else concat += information[z];
                                                if(z < back) concat += ",";
                                        }
                                }
                                else {
                                        // if concatenation is not needed between multiple elements, just remove the quotes at the start and back of the element
                                        concat += information[start].substring(1, information[start].length() - 1);
                                }

                                // replace all double quotes within the data to a single quote
                                concat = concat.replace("\"\"", "\"");

                                // put concatenated data point in result and iterate
                                result[i] = concat;
                                j = back + 1;
                        }
                }
		// instantiate instance variables
		title = result[0];
		artist = result[1];
		genre = result[2];
		year = Integer.parseInt(result[3]);
		bpm = Integer.parseInt(result[4]);
		energy = Integer.parseInt(result[5]);
		danceability = Integer.parseInt(result[6]);
		loudness = Integer.parseInt(result[7]);
		liveness = Integer.parseInt(result[8]);

	}

	@Override
	public String getTitle(){
		return title;
	}

	@Override
	public String getArtist(){
		return artist;
	}

	@Override
	public String getGenres(){
		return genre;
	}

	@Override
	public int getYear(){
		return year;
	}
    
	@Override
   	public int getBPM(){
		return bpm;
	}
    
	@Override
	public int getEnergy(){
		return energy;
	}
    
	@Override
    	public int getDanceability(){
		return danceability;
	}
    	
	@Override
    	public int getLoudness(){
		return loudness;
	}
    	
	@Override
    	public int getLiveness(){
		return liveness;
	}

	@Override
	public int compareTo(SongInterface song){
		if(this.getDanceability() > song.getDanceability()) return 1;

		if(this.getDanceability() == song.getDanceability()) return 0;

		return -1;

	}
   
}
