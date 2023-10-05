package com.example.ticgame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button resetButton;
    private TextView turnTextView;
    private TableLayout mainBoard;

    private boolean isPlayerX = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainBoard = findViewById(R.id.mainBoard);
        turnTextView = findViewById(R.id.turn);
        resetButton = findViewById(R.id.reset);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
            }
        });

        setupBoard();
    }

    private void setupBoard() {
        for (int i = 0; i < mainBoard.getChildCount(); i++) {
            TableRow row = (TableRow) mainBoard.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView cell = (TextView) row.getChildAt(j);
                cell.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        TextView cell = (TextView) v;
        if (cell.getText().toString().equals("")) {
            if (isPlayerX) {
                cell.setText("X");
                turnTextView.setText("Turn: O");
            } else {
                cell.setText("O");
                turnTextView.setText("Turn: X");
            }
            isPlayerX = !isPlayerX;
            checkForWinner();
        }
    }

    private void checkForWinner() {
        String[][] board = new String[3][3];
        for (int i = 0; i < mainBoard.getChildCount(); i++) {
            TableRow row = (TableRow) mainBoard.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView cell = (TextView) row.getChildAt(j);
                board[i][j] = cell.getText().toString();
            }
        }

        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].equals("")) {
                displayWinner(board[i][0]);
                return;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j].equals(board[1][j]) && board[1][j].equals(board[2][j]) && !board[0][j].equals("")) {
                displayWinner(board[0][j]);
                return;
            }
        }

        // Check diagonals
        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].equals("")) {
            displayWinner(board[0][0]);
            return;
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].equals("")) {
            displayWinner(board[0][2]);
            return;
        }

        // Check for a draw
        boolean isDraw = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("")) {
                    isDraw = false;
                    break;
                }
            }
        }

        if (isDraw) {
            displayWinner("Draw");
        }
    }

    private void displayWinner(String winner) {
        if (winner.equals("Draw")) {
            turnTextView.setText("It's a draw!");
        } else {
            turnTextView.setText("Player " + winner + " wins!");
        }

        // Disable further clicks on the board
        for (int i = 0; i < mainBoard.getChildCount(); i++) {
            TableRow row = (TableRow) mainBoard.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView cell = (TextView) row.getChildAt(j);
                cell.setClickable(false);
            }
        }
    }


    private void resetBoard() {
        for (int i = 0; i < mainBoard.getChildCount(); i++) {
            TableRow row = (TableRow) mainBoard.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView cell = (TextView) row.getChildAt(j);
                cell.setText("");
            }
        }
        turnTextView.setText("Turn: X");
        isPlayerX = true;
    }
}
