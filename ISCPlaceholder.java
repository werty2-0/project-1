import java.util.Iterator;
import java.util.ArrayList;

public class ISCPlaceholder
    implements IterableSortedCollection<SongInterface> {

    private SongInterface value;
    
    public boolean insert(SongInterface data)
	throws NullPointerException, IllegalArgumentException {
	value = data;
	return true;
    }

    public boolean contains(Comparable<SongInterface> data) {
	return true;
    }

    public boolean isEmpty() {
	return false;
    }
    
    public int size() {
	return 603;
    }

    public void clear() {
    }

    public void setIterationStartPoint(Comparable<SongInterface> startPoint) {	
    }

    public Iterator<SongInterface> iterator() {
	
	ArrayList<SongInterface> list = new ArrayList<>();
	SongInterface song1 = new Song("Anaconda,Nicki Minaj,dance pop,2014,130,60,96,-6,21");
	SongInterface song2 = new Song("Bad Liar,Selena Gomez,dance pop,2018,121,41,97,-6,8");
	SongInterface song3 = new Song("Drip (feat. Migos),Cardi B,pop,2018,130,59,97,-8,8");

	list.add(song1);
	list.add(song2);
	list.add(song3);
	return list.iterator();
    }
}
