package music;

import java.util.*;

/**
 * This class represents a library of song playlists.
 *
 * An ArrayList of Playlist objects represents the various playlists 
 * within one's library.
 * 
 * @author Jeremy Hui
 * @author Vian Miranda
 */
public class PlaylistLibrary {

    private ArrayList<Playlist> songLibrary; // contains various playlists
    List<Playlist> playLists=new ArrayList<>();

    /**
     * DO NOT EDIT!
     * Constructor for Library.
     * 
     * @param songLibrary passes in ArrayList of playlists
     */
    public PlaylistLibrary(ArrayList<Playlist> songLibrary) {
        this.songLibrary = songLibrary;
    }

    /**
     * DO NOT EDIT!
     * Default constructor for an empty library. 
     */
    public PlaylistLibrary() {
        this(null);
    }

    /**
     * This method reads the songs from an input csv file, and creates a 
     * playlist from it.
     * Each song is on a different line.
     * 
     * 1. Open the file using StdIn.setFile(filename);
     * 
     * 2. Declare a reference that will refer to the last song of the circular linked list.
     * 
     * 3. While there are still lines in the input file:
     *      1. read a song from the file
     *      2. create an object of type Song with the song information
     *      3. Create a SongNode object that holds the Song object from step 3.2.
     *      4. insert the Song object at the END of the circular linked list (use the reference from step 2)
     *      5. increase the count of the number of songs
     * 
     * 4. Create a Playlist object with the reference from step (2) and the number of songs in the playlist
     * 
     * 5. Return the Playlist object
     * 
     * Each line of the input file has the following format:
     *      songName,artist,year,popularity,link
     * 
     * To read a line, use StdIn.readline(), then use .split(",") to extract 
     * fields from the line.
     * 
     * If the playlist is empty, return a Playlist object with null for its last, 
     * and 0 for its size.
     * 
     * The input file has Songs in decreasing popularity order.
     * 
     * DO NOT implement a sorting method, PRACTICE add to end.
     * 
     * @param filename the playlist information input file
     * @return a Playlist object, which contains a reference to the LAST song 
     * in the ciruclar linkedlist playlist and the size of the playlist.
     */
    public Playlist createPlaylist(String filename) {

        StdIn.setFile(filename);

        SongNode lastS = null;

        int sCount = 0;

        SongNode sNode ;

        while (!StdIn.isEmpty()) {
            String line = StdIn.readLine();
            String[] field = line.split(",");

            String name = field[0];
            String artist = field[1];
            int year = Integer.parseInt(field[2]);
            int popular = Integer.parseInt(field[3]);
            String link = field[4];
            Song songs = new Song(name, artist, year, popular, link);

         sNode = new SongNode(songs,null);
        if (lastS != null) {
            sNode.setNext(lastS.getNext());
            lastS.setNext(sNode);
            lastS = sNode;
        } else {
            sNode.setNext(sNode);
            lastS= sNode;
        }
        lastS = sNode;
        sCount++;
    }
            Playlist playList = new Playlist(lastS,sCount);
            playLists.add(playList);
            return playList;
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * This method is already implemented for you. 
     * 
     * Adds a new playlist into the song library at a certain index.
     * 
     * 1. Calls createPlayList() with a file containing song information.
     * 2. Adds the new playlist created by createPlayList() into the songLibrary.
     * 
     * Note: initialize the songLibrary if it is null
     * 
     * @param playlist the playlist information input file
     * @param songLibrary2 the index of the location where the playlist will 
     * be added 
     */
    public void addPlaylist(String filename, int playlistIndex) {
        
        /* DO NOT UPDATE THIS METHOD */

        if ( songLibrary == null ) {
            songLibrary = new ArrayList<Playlist>();
        }
        if ( playlistIndex >= songLibrary.size() ) {
            songLibrary.add(createPlaylist(filename));
        } else {
            songLibrary.add(playlistIndex, createPlaylist(filename));
        }        
        
        
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * This method is already implemented for you.
     * 
     * It takes a playlistIndex, and removes the playlist located at that index.
     * 
     * @param playlistIndex the index of the playlist to remove
     * @return true if the playlist has been deleted
     */
    public boolean removePlaylist(int playlistIndex) {
        /* DO NOT UPDATE THIS METHOD */

        if ( songLibrary == null || playlistIndex >= songLibrary.size() ) {
            return false;
        }

        songLibrary.remove(playlistIndex);
            
        return true;
    }
    
    /** 
     * 
     * Adds the playlists from many files into the songLibrary
     * 
     * 1. Initialize the songLibrary
     * 
     * 2. For each of the filenames
     *       add the playlist into songLibrary
     * 
     * The playlist will have the same index in songLibrary as it has in
     * the filenames array. For example if the playlist is being created
     * from the filename[i] it will be added to songLibrary[i]. 
     * Use the addPlaylist() method. 
     * 
     * @param filenames an array of the filenames of playlists that should be 
     * added to the library
     */
    public void addAllPlaylists(String[] filenames) {
        songLibrary= new ArrayList<Playlist>();
        for (String filename : filenames) {
            addPlaylist(filename, songLibrary.size());
        }
    }

    /**
     * This method adds a song to a specified playlist at a given position.
     * 
     * The first node of the circular linked list is at position 1, the 
     * second node is at position 2 and so forth.
     * 
     * Return true if the song can be added at the given position within the 
     * specified playlist (and thus has been added to the playlist), false 
     * otherwise (and the song will not be added). 
     * 
     * Increment the size of the playlist if the song has been successfully
     * added to the playlist.
     * 
     * @param playlistIndex the index where the playlist will be added
     * @param pos the position inthe playlist to which the song 
     * is to be added 
     * @param song the song to add
     * @return true if the song can be added and therefore has been added, 
     * false otherwise. 
     */
    public boolean insertSong(int playlistIndex, int pos, Song song) {

        if(playlistIndex < 0 ||playlistIndex >= songLibrary.size()){
            return false;
        }
        
        Playlist playlist = songLibrary.get(playlistIndex);
        if(pos<1){
            return false;
    }

        int size = playlist.getSize();
        if(pos > size + 1 ){
            return false;

    }
        if (pos == size + 1){
            pos = size + 1;

    }
        SongNode sNode = new SongNode();
        sNode.setSong(song);
        if (playlist.getLast() == null) {
            playlist.setLast(sNode);
            sNode.setNext(sNode);

        } 
            else {
                if (pos ==1){
                sNode.setNext(playlist.getLast().getNext());
                playlist.getLast().setNext(sNode);
        } 
                else {
                    SongNode previousNode = playlist.getLast().getNext();
                        for (int x = 1; x < pos - 1; x++) {
                            previousNode = previousNode.getNext();
                }
                            sNode.setNext(previousNode.getNext());
                            previousNode.setNext((sNode));
        }
        if (pos == size + 1) {
        playlist.setLast(sNode);
        }
    }
        playlist.setSize(size + 1);
        return true;
        }
    
    

    /**
     * This method removes a song at a specified playlist, if the song exists. 
     *
     * Use the .equals() method of the Song class to check if an element of 
     * the circular linkedlist matches the specified song.
     * 
     * Return true if the song is found in the playlist (and thus has been 
     * removed), false otherwise (and thus nothing is removed). 
     * 
     * Decrease the playlist size by one if the song has been successfully
     * removed from the playlist.
     * 
     * @param playlistIndex the playlist index within the songLibrary where 
     * the song is to be added.
     * @param song the song to remove.
     * @return true if the song is present in the playlist and therefore has 
     * been removed, false otherwise.
     */
    public boolean removeSong(int playlistIndex, Song song) {

        Playlist ourplaylist = songLibrary.get(playlistIndex);

        if (ourplaylist.getSize() != 0) {
            SongNode currentNode = ourplaylist.getLast().getNext();
            SongNode previousNode = ourplaylist.getLast();
            int ourPlaylistSize = ourplaylist.getSize();

            for (int x = 1; x<= ourPlaylistSize; x++) {
                if (currentNode.getSong().equals(song)) {
                    if (ourPlaylistSize == 1) {
                        ourplaylist.setLast(null);
                    } 
                    else {
                        previousNode.setNext(currentNode.getNext());
                        if (currentNode == ourplaylist.getLast()) {
                            ourplaylist.setLast(previousNode);
                        }
                    }
                    ourplaylist.setSize(ourPlaylistSize - 1);
                    return true;
                }
                previousNode = currentNode;
                currentNode = currentNode.getNext();
            }
        }
        return false;
    }

    /**
     * This method reverses the playlist located at playlistIndex`
     * 
     * Each node in the circular linked list will point to the element that 
     * came before it.
     * 
     * After the list is reversed, the playlist located at playlistIndex will 
     * reference the first SongNode in the original playlist (new last).
     * 
     * @param playlistIndex the playlist to reverse
     */
    public void reversePlaylist(int playlistIndex) {

        Playlist playlist = songLibrary.get(playlistIndex);
    
        if (playlist == null || !(playlist.getSize()>0)) {
            return; 
        }
    
        SongNode current = playlist.getLast().getNext(); 
        SongNode previous = playlist.getLast();
        SongNode next;
        SongNode first = current;
    
        for (int i =0; i <playlist.getSize(); i++) {
            next = current.getNext();
            current.setNext(previous);
            previous = current;
            current = next;
         }


        playlist.setLast(first);
        
    }

    /**
     * This method merges two playlists.
     * 
     * Both playlists have songs in decreasing popularity order. The resulting 
     * playlist will also be in decreasing popularity order.
     * 
     * You may assume both playlists are already in decreasing popularity 
     * order. If the songs have the same popularity, add the song from the 
     * playlist with the lower playlistIndex first.
     * 
     * After the lists have been merged:
     *  - store the merged playlist at the lower playlistIndex
     *  - remove playlist at the higher playlistIndex 
     * 
     * 
     * @param playlistIndex the first playlist to merge into one playlist
     * @param playlistIndex1 the second playlist to merge into one playlist
     */
    public void mergePlaylists(int playlistIndex, int playlistIndex1) {
        if (playlistIndex != playlistIndex1) {
            int index;
            int index2;
            Playlist a = new Playlist();
            Playlist b = new Playlist();
            int playlistsz=songLibrary.get(playlistIndex).getSize() ;
            int playlistsz1=songLibrary.get(playlistIndex1).getSize() ;
           
            if (songLibrary.get(playlistIndex).getSize() == 0) {
                if (songLibrary.get(playlistIndex1).getSize() != 0) {
                    a.setLast(songLibrary.get(playlistIndex1).getLast());
                }
            } 
            else if (playlistsz1== 0) {
                if (playlistsz != 0) {
                    a.setLast(songLibrary.get(playlistIndex).getLast());
                }
            } 
            else {
                if (songLibrary.get(playlistIndex).getLast().getSong().getPopularity() > songLibrary.get(playlistIndex1).getLast().getSong().getPopularity()) {
                a = songLibrary.get(playlistIndex1);
                    index = playlistIndex1;
                    b = songLibrary.get(playlistIndex);
                    index2 = playlistIndex;
                } else 
                if (songLibrary.get(playlistIndex).getLast().getSong().getPopularity() < songLibrary.get(playlistIndex1).getLast().getSong().getPopularity()) {
                    a = songLibrary.get(playlistIndex);
                    index = playlistIndex;
                    b = songLibrary.get(playlistIndex1);
                    index2 = playlistIndex1;
                } 
                else {
                    if (playlistIndex < playlistIndex1) {
                        a = songLibrary.get(playlistIndex1);
                        index = playlistIndex1;
                        b = songLibrary.get(playlistIndex);
                        index2 = playlistIndex; 
                    } 
                    else {
                        a = songLibrary.get(playlistIndex);
                        index = playlistIndex;
                        b = songLibrary.get(playlistIndex1);
                        index2 = playlistIndex1;
                    }
                }
                SongNode tempvar = a.getLast();
                SongNode tempvar1 = b.getLast().getNext();
                for(int i = 0; i < b.getSize(); i++) {
                    while(tempvar.getNext().getSong().getPopularity() >= tempvar1.getSong().getPopularity()) {
                        if (tempvar.getNext().getSong().getPopularity() == tempvar1.getSong().getPopularity()) {
                            if (index2 < index) {break;}
                        }
                        tempvar = tempvar.getNext();
                    }
                    SongNode tempvar2 = new SongNode(tempvar1.getSong(), tempvar.getNext());
                    tempvar.setNext(tempvar2);
                    tempvar = tempvar.getNext();
                    tempvar1 = tempvar1.getNext();
                }
            }
            a.setSize(songLibrary.get(playlistIndex).getSize() + songLibrary.get(playlistIndex1).getSize());
            if (playlistIndex < playlistIndex1) {
                songLibrary.set(playlistIndex, a);
                songLibrary.remove(playlistIndex1);
            } 
            else {
                songLibrary.set(playlistIndex1, a);
                songLibrary.remove(playlistIndex);
            }
        }
    }

    /**
     * This method shuffles a specified playlist using the following procedure:
     * 
     az* 1. Create a new playlist to store the shuffled playlist in.
     * 
     * 2. While the size of the original playlist is not 0, randomly generate a number 
     * using StdRandom.uniformInt(1, size+1). Size contains the current number
     * of items in the original playlist.
     * 
     * 3. Remove the corresponding node from the original playlist and insert 
     * it into the END of the new playlist (1 being the first node, 2 being the 
     * second, etc). 
     * 
     * 4. Update the old playlist with the new shuffled playlist.
     *    
     * @param index the playlist to shuffle in songLibrary
     */
    public void shufflePlaylist(int playlistIndex) {
        
        if (playlistIndex < 0 || playlistIndex >= songLibrary.size()) {
            return; 
        }
    
        Playlist orgPlaylist = songLibrary.get(playlistIndex);
    
        ArrayList<Song> songs = new ArrayList<>();
    
        SongNode currentNode = orgPlaylist.getLast().getNext();
        int sz = orgPlaylist.getSize();
    
        for (int i = 0; i < sz; i++) {
            songs.add(currentNode.getSong());
            currentNode = currentNode.getNext();
        }
    
        Collections.shuffle(songs, new Random(2023));
    
        Playlist shuffledPlaylist = new Playlist();
        SongNode previousNode = null;
    
        for (Song song : songs) {
            SongNode newNode = new SongNode(song, null);
            if (previousNode == null) {
                shuffledPlaylist.setLast(newNode);
                newNode.setNext(newNode);
            } else {
                newNode.setNext(previousNode.getNext());
                previousNode.setNext(newNode);
            }
            shuffledPlaylist.setSize(shuffledPlaylist.getSize() + 1);
            previousNode = newNode;
        }
    
        songLibrary.set(playlistIndex, shuffledPlaylist);
}

    /**
     * This method sorts a specified playlist using linearithmic sort.
     * 
     * Set the playlist located at the corresponding playlistIndex
     * in decreasing popularity index order.
     * 
     * This method should  use a sort that has O(nlogn), such as with merge sort.
     * 
     * @param playlistIndex the playlist to shuffle
     */
    public void sortPlaylist ( int playlistIndex ) {
        
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * Plays playlist by index; can use this method to debug.
     * 
     * @param playlistIndex the playlist to print
     * @param repeats number of times to repeat playlist
     * @throws InterruptedException
     */
    public void playPlaylist(int playlistIndex, int repeats) {
        /* DO NOT UPDATE THIS METHOD */

        final String NO_SONG_MSG = " has no link to a song! Playing next...";
        if (songLibrary.get(playlistIndex).getLast() == null) {
            StdOut.println("Nothing to play.");
            return;
        }

        SongNode ptr = songLibrary.get(playlistIndex).getLast().getNext(), first = ptr;

        do {
            StdOut.print("\r" + ptr.getSong().toString());
            if (ptr.getSong().getLink() != null) {
                StdAudio.play(ptr.getSong().getLink());
                for (int ii = 0; ii < ptr.getSong().toString().length(); ii++)
                    StdOut.print("\b \b");
            }
            else {
                StdOut.print(NO_SONG_MSG);
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException ex) {
                    ex.printStackTrace();
                }
                for (int ii = 0; ii < NO_SONG_MSG.length(); ii++)
                    StdOut.print("\b \b");
            }

            ptr = ptr.getNext();
            if (ptr == first) repeats--;
        } while (ptr != first || repeats > 0);
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * Prints playlist by index; can use this method to debug.
     * 
     * @param playlistIndex the playlist to print
     */
    public void printPlaylist(int playlistIndex) {
        StdOut.printf("%nPlaylist at index %d (%d song(s)):%n", playlistIndex, songLibrary.get(playlistIndex).getSize());
        if (songLibrary.get(playlistIndex).getLast() == null) {
            StdOut.println("EMPTY");
            return;
        }
        SongNode ptr;
        for (ptr = songLibrary.get(playlistIndex).getLast().getNext(); ptr != songLibrary.get(playlistIndex).getLast(); ptr = ptr.getNext() ) {
            StdOut.print(ptr.getSong().toString() + " -> ");
        }
        if (ptr == songLibrary.get(playlistIndex).getLast()) {
            StdOut.print(songLibrary.get(playlistIndex).getLast().getSong().toString() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    public void printLibrary() {
        if (songLibrary.size() == 0) {
            StdOut.println("\nYour library is empty!");
        } else {
                for (int ii = 0; ii < songLibrary.size(); ii++) {
                printPlaylist(ii);
            }
        }
    }

    /*
     * Used to get and set objects.
     * DO NOT edit.
     */
     public ArrayList<Playlist> getPlaylists() { return songLibrary; }
     public void setPlaylists(ArrayList<Playlist> p) { songLibrary = p; }
}


