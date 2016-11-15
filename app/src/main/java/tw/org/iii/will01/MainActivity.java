package tw.org.iii.will01;

import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.annotation.IntegerRes;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    private EditText input;
    private String strAnswer;
    private TextView mesg;
    private int intCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (EditText) findViewById(R.id.input);
        mesg = (TextView) findViewById(R.id.mesg);

        strAnswer = createAnswer(3);

    }
    // 按下按鈕做的事
    public void guess(View v) {
        intCounter++;
        String guessText = input.getText().toString();
        String result = checkAB(strAnswer, guessText);
        mesg.append(intCounter + ". " + guessText + " => " + result + "\n");

        if (result.equals("3A0B")) {
            showDialog(true);
        } else if (intCounter == 10) {
            showDialog(false);
        }
        input.setText("");
    }

    // show dialog
    private void showDialog(boolean isWinner) {
        AlertDialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(isWinner? "Winner" : "Loser");
        builder.setMessage(isWinner? "恭喜老爺，賀喜夫人!" : "謎底為 : " + strAnswer);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restart(null);
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    public void restart(View v) {
        mesg.setText("");
        intCounter = 0;
        strAnswer = createAnswer(3);
    }

    public void exit(View v) {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }

    static String createAnswer(int n){
        // 洗牌
        int[] poker = new int[n]; // poker[0] = 0, ....
        int temp; boolean isRepeat;
        for (int i=0; i<poker.length; i++){
            do{
                temp = (int)(Math.random()*10);
                // 檢查機制
                isRepeat = false;
                for (int j=0; j<i; j++){
                    if (poker[j] == temp){
                        // 此時重複了
                        isRepeat = true;
                        break;
                    }
                }
            }while (isRepeat);
            poker[i] = temp;
            //System.out.println(poker[i]);
        }
        String ret = "";
        for (int v : poker) ret += v;

        return ret;
    }
    static String checkAB(String a, String g){
        int A, B; A = B = 0;
        for (int i=0; i<a.length(); i++){
            if (g.charAt(i) == a.charAt(i)){
                A++;
            }else if (a.indexOf(g.charAt(i)) != -1){
                B++;
            }
        }
        return A + "A" + B + "B";
    }
}
