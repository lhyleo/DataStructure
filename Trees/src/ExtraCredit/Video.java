package ExtraCredit;

public class Video {
    public static void main(String[] args) {
        VideoLibrary videoLibrary = new VideoLibrary();
        videoLibrary.add("mv", "Movie", 2018, "Leo", "leo");
        videoLibrary.add("mve", "Movie", 2018, "Leo", "leo");
        videoLibrary.usingMovie("mve");
        videoLibrary.usingMovie("mve");
        videoLibrary.usingMovie("mv");
        videoLibrary.usingMovie("mv");
        System.out.println(videoLibrary.getMostUsedMovie());
        System.out.println(videoLibrary.getDirectorsMovie("Leo"));
    }
}
