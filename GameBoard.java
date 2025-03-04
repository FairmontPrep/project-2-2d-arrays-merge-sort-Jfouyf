import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard extends JFrame {
    private static final int SIZE = 8;
    private JPanel[][] squares = new JPanel[SIZE][SIZE];
    private ImageIcon exampleIcon;
    private ImageIcon[] characterIcons;
    public String[][] piecesArray;
    
    private static final String[] characterNames = {"Squirtle", "Pikachu", "Bulbasaur", "Charizard", "Mewtwo", "Eevee"};
    private static final String[] characterHP = {"HP:200", "HP:150", "HP:180", "HP:190", "HP:230", "HP:280"};
    private static final String[] characterImages = {
        "/Users/ajsun/Documents/GitHub/project-2-2d-arrays-merge-sort-Jfouyf/temp2.png",  // Squirtle
        "/Users/ajsun/Documents/GitHub/project-2-2d-arrays-merge-sort-Jfouyf/11111sssd.jpg",  // Pikachu
        "/Users/ajsun/Documents/GitHub/project-2-2d-arrays-merge-sort-Jfouyf/download.jpg",  // Bulbasaur
        "/Users/ajsun/Documents/GitHub/project-2-2d-arrays-merge-sort-Jfouyf/sss.png",  // Charizard
        "/Users/ajsun/Documents/GitHub/project-2-2d-arrays-merge-sort-Jfouyf/images.jpg",  // Mewtwo
        "/Users/ajsun/Documents/GitHub/project-2-2d-arrays-merge-sort-Jfouyf/ssfds.jpg",  // Eevee
};
    public GameBoard()  {
        setTitle("Chess Board");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));

        // Initialize image icons for characters
        characterIcons = new ImageIcon[characterImages.length];
        for (int i = 0; i < characterImages.length; i++) {
            characterIcons[i] = new ImageIcon(characterImages[i]);
        }

        // Create the 2D array to store character info
        piecesArray = new String[characterNames.length][3];  // Store name, HP, image path
        for (int i = 0; i < characterNames.length; i++) {
            piecesArray[i][0] = characterNames[i];
            piecesArray[i][1] = characterHP[i];
            piecesArray[i][2] = characterImages[i];
        }

        // Sort characters by HP (mergeSort should be used here)
        mergeSort(piecesArray, 0, piecesArray.length - 1);

        // Initialize the color pattern for the board
        initializeColorPattern();

        // Initialize the board
        initializeBoard();

        // Place the characters randomly on 16 squares
        placeCharactersRandomly();
    }
    private Color[][] colorPattern = new Color[SIZE][SIZE];
    private void initializeColorPattern() {
        // Define the color pattern as per the given specification
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                colorPattern[row][col] = getColorForPosition(row, col);
            }
        }
    }

    private void initializeBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                squares[row][col] = new JPanel(new BorderLayout());

                // Set the color from the colorPattern array
                squares[row][col].setBackground(colorPattern[row][col]);

                // Adding characters to the board in sorted order (by HP)
                if (row == 0 && col == 0) {  // Example of placing first character
                    ImageIcon icon = characterIcons[0];
                    Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                    JLabel pieceLabel = new JLabel(new ImageIcon(scaledImage));
                    JLabel textLabel = new JLabel(piecesArray[0][1], SwingConstants.CENTER);
                    squares[row][col].add(pieceLabel, BorderLayout.CENTER);
                    squares[row][col].add(textLabel, BorderLayout.SOUTH);
                }

                add(squares[row][col]);
            }
        }
    }

    private Color getColorForPosition(int row, int col) {
        // Color mapping based on row and column positions using a 2D array
        if (row == 0 && col == 0) return new Color(255, 165, 0); // Orange
        if ((row == 0 && (col == 1 || col == 2)) || (row == 1 && (col == 0 || col == 1)) || (row == 2 && col == 0)) return new Color(255, 204, 0); // Deep Yellow
        if ((row == 0 && (col == 3)) || (row == 1 && (col == 2 || col == 3)) || (row == 2 && (col == 2 || col == 1)) || (row == 3 && (col == 1 || col == 0))) return new Color(255, 255, 0); // Yellow
        if ((row == 0 && col == 4) || (row == 1 && col == 4) || (row == 2 && col == 3) || (row == 2 && col == 4) || (row == 3 && (col == 3 || col == 2)) || (row == 4 && (col == 2 || col == 1 || col == 0))) return new Color(0, 0, 255); // Blue
        if ((row == 0 && col == 5) || (row == 1 && col == 5) || (row == 2 && col == 5) || (row == 3 && (col == 5 || col == 4)) || ( row == 4 && (col == 4 || col == 3)) || (row == 5 && (col == 0 || col == 1 || col == 2 || col == 3))) return new Color(204, 153, 255); // Light Purple
        if ((row == 0 && col == 6) || (row == 1 && col == 6) || (row == 2 && col == 6) || (row == 3 && col == 6) || (row == 4 && (col == 6 || col == 5 )) || (row == 5 && (col == 5 || col == 4)) || (row == 6 && (col == 1 || col == 2 || col == 3 || col ==4 || col == 0))) return new Color(128, 0, 128); // Dark Purple
        if ((row == 0 && col == 7) || (row == 1 && col == 7) || (row == 2 && col == 7) || (row == 3 && col == 7) || ( row == 4 && col == 7 ) || (row == 5 && (col == 7 || col == 6)) || (row == 6 && ( col == 6 || col== 5)) || (row == 7 && ( col == 5 || col == 4 || col == 3 || col == 2 || col ==1 || col == 0))) return new Color(255, 192, 203); // Pink
        return new Color(255, 160, 122); // Light Red
    }
    // Method to randomly place characters in 16 random squares on the board
    // Method to randomly place 6 characters in 32 random squares on the board
private void placeCharactersRandomly() {
    ArrayList<Integer> usedSquares = new ArrayList<>();  // List to keep track of squares already used
    Random random = new Random();

    // Generate 32 unique random positions on the 8x8 board
    while (usedSquares.size() < 32) {
        int pos = random.nextInt(SIZE * SIZE);  // Randomly select a square index
        if (!usedSquares.contains(pos)) {
            usedSquares.add(pos);  // Add it to the list of used squares
            int row = pos / SIZE;  // Calculate the row from the square index
            int col = pos % SIZE;  // Calculate the column from the square index

            // Cycle through the characters when placing them (since there are 6 characters)
            int characterIndex = usedSquares.size() % characterNames.length;  // 6 characters, cycle through them

            // Get the image and HP for the current character
            ImageIcon icon = characterIcons[characterIndex];
            Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);  // Scale image for size
            JLabel pieceLabel = new JLabel(new ImageIcon(scaledImage));
            JLabel textLabel = new JLabel(piecesArray[characterIndex][1], SwingConstants.CENTER);

            // Add the character image and HP to the square
            squares[row][col].add(pieceLabel, BorderLayout.CENTER);
            squares[row][col].add(textLabel, BorderLayout.SOUTH);
        }
    }
}



    // add your merge sort method here
    // add a comment to every line of code that describes what the line is accomplishing
    // your mergeSort method does not have to return any value
    private void mergeSort(String[][] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private void merge(String[][] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        String[][] leftArray = new String[n1][3];
        String[][] rightArray = new String[n2][3];

        System.arraycopy(arr, left, leftArray, 0, n1);
        System.arraycopy(arr, mid + 1, rightArray, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (Integer.parseInt(leftArray[i][1].split(":")[1]) <= Integer.parseInt(rightArray[j][1].split(":")[1])) {
                arr[k++] = leftArray[i++];
            } else {
                arr[k++] = rightArray[j++];
            }
        }

        while (i < n1) {
            arr[k++] = leftArray[i++];
        }

        while (j < n2) {
            arr[k++] = rightArray[j++];
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameBoard board = new GameBoard();
            board.setVisible(true);
        });
    }
}
