
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

		//split columns
      		String[] information = row.split(",");

      		
      		String[] result  = new String[14];

      		int i = 0; // tracking position in result
      		int j = 0; // tracking position in splitted information
      
      		while(i < 14){

	              	// if it is not special, put the information straight into the result
        	      	if(j > information.length - 1) break;
              		if(!information[j].contains("\"")) {
                      		result[i] = information[j];
                     		i++;
                    		j++;
              		}

              		// if information has extra comma, we have to concatnate with the next spot
              		else{
                      		//find next open spot
                      		int y = j + 1;
		      		// keep looking in the split array until find whole interval 
                      		while(information[y].contains("\"")){
                      			y++;
                     		}
				// create string to concatanate with
			      	String concat = "";
	
			// Go through the entire interval and start concatnating the words together, getting rid of the quotation marks;
		        for(int z = j; z < y; z++) {
	              		if(z == j) concat += information[z].substring(1);
              			else if(z == y - 1) concat += information[z].substring(0, information[z].length() - 1);
              			else concat += information[z];
              			if(z + 1 != y) concat += ",";
		      	}
	      		// put concatnated information into our result and iterate further
              		result[i] = concat;
              		i++;
              		j = y + 1;
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
