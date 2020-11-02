//* Baseline
//TODO: init custom field
//TODO: draw field
//TODO: make player turn
//TODO: set cell
//TODO: make AI turn
//TODO: check win condition
//TODO: check draw condition
//* Additional
//TODO: generalize size
//TODO: block player


import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    public static char PLAYER_TOKEN = 'X';
    public static char AI_TOKEN = 'O';
    public static char VOID_TOKEN = '-';
    public static int SIZE = 5;
    public static int TOKENS_TO_WIN = 4;

    public static void main(String[] args) {

        char[][] field = new char[SIZE][SIZE];

        System.out.println("Игра в \"Крестики-нолики\".");

        initField(field);
        drawField(field);

        while(true) {

            System.out.println("Ход игрока.");
            if (makePlayerTurn(field)) {
                System.out.print("Победил человек.");
                break;
            }

            if (checkDraw(field)) {
                System.out.print("Ничья.");
                break;
            }
            System.out.println("Ход компьютера.");
            if (makeAITurn(field)) {
                System.out.print("Победил компьютер.");
                break;

            }
            if (checkDraw(field)) {
                System.out.print("Ничья.");
                break;
            }
        }
    }

    private static boolean checkDraw(char[][] field) {
        int count = field.length * field.length;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != VOID_TOKEN) {
                    count--;
                }
            }
        }
        return count == 0;
    }

    private static boolean makeAITurn(char[][] field) {

        if (!blockPlayer(field)) {
            setRandomCell(field, AI_TOKEN);
        }

        drawField(field);

        return checkWinCondition(field, AI_TOKEN);
    }

    private static boolean blockPlayer(char[][] field) {

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                char[][] copiedField;
                copiedField = copyArray(field);
                if (field[i][j] == VOID_TOKEN) {
                    setCell(copiedField, i, j, PLAYER_TOKEN);
                    if (checkWinCondition(copiedField, PLAYER_TOKEN)) {
                        setCell(field, i, j, AI_TOKEN);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static char[][] copyArray(char[][] array) {
        char[][] copiedArray = new char[array.length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j <array[i].length; j++) {
                copiedArray[i][j] = array[i][j];
            }
        }
        return copiedArray;
    }

    private static void setRandomCell(char[][] field, char token) {
        int x, y;

        do {
            x = new Random().nextInt(field.length);
            y = new Random().nextInt(field.length);
        } while (field[x][y] != VOID_TOKEN);

        setCell(field, x, y, token);
    }

    private static boolean makePlayerTurn(char[][] field) {
        int x, y;
        Scanner scanner = new Scanner(System.in);

        do {
            do {
                System.out.print("Введите координату X: ");
                x = scanner.nextInt();
                System.out.print("Введите координату Y: ");
                y = scanner.nextInt();
            } while (x <=0 || x > field.length || y <= 0 || y > field.length);
        } while (field[x - 1][y - 1] != VOID_TOKEN);

        setCell(field, x - 1, y - 1, PLAYER_TOKEN);
        drawField(field);
        return checkWinCondition(field, PLAYER_TOKEN);
    }

    private static boolean checkWinCondition(char[][] field, char token) {
        if (checkWinVertically(field, token)) {
            return true;
        }
        if (checkWinHorizontally(field, token)) {
            return true;
        }
        return checkWinDiagonally(field, token);
    }

    private static boolean checkWinDiagonally(char[][] field, char token) {

        char[][] kernel = new char[TOKENS_TO_WIN][TOKENS_TO_WIN];


        for (int m = 0; m < field.length; m++) {
            for (int n = 0; n < field[m].length; n++) {

                if (m + TOKENS_TO_WIN <= field.length && n + TOKENS_TO_WIN <= field.length) {

                    for (int i = 0; i < kernel.length; i++) {
                        for (int j = 0; j < kernel[i].length; j++) {
                            kernel[i][j] = field[i + n][j + m];
                        }
                    }

                    if (checkPrimaryDiagonal(kernel, token)) {
                        return true;
                    }

                    if (checkSecondaryDiagonal(kernel, token)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkSecondaryDiagonal(char[][] field, char token) {
        int count = 0;

        for (int i = 0; i < field.length; i++) {

            if (field[i][field.length - i - 1] == token) {
                count++;
            } else {
                count--;
            }
            if (count == TOKENS_TO_WIN) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkPrimaryDiagonal(char[][] field, char token) {
        int count = 0;

        for (int i = 0; i < field.length; i++) {

            if (field[i][i] == token) {
                count++;
            } else {
                count--;
            }
            if (count == TOKENS_TO_WIN) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkWinVertically(char[][] field, char token) {
        int count;

        for (int i = 0; i < field.length; i++) {
            count = 0;
            for (int j = 0; j < field.length; j++) {
                if (field[j][i] == token) {
                    count++;
                }
                else {
                    count = 0;
                }
                if (count == TOKENS_TO_WIN) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkWinHorizontally(char[][] field, char token) {
        int count;

        for (int i = 0; i < field.length; i++) {
            count = 0;
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == token) {
                    count++;
                }
                else {
                    count = 0;
                }
                if (count == TOKENS_TO_WIN) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void setCell(char[][] field, int x, int y, char token) {
        field[x][y] = token;
    }


    private static void drawField(char[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void initField(char[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = VOID_TOKEN;
            }
        }
    }
}

