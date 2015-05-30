package dma.erinnerungsspiel;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    public static final String MESSAGE = "Message";
    private Random zufallsGenerator = new Random();
    private int zufallszahl;
    public static final int REQUEST_CODE = 1;
    public static List<Integer> numbers = new ArrayList<Integer>();
    public static int numbersPosition = 0;
    private TextView textResult, textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zufallszahl = zufallsGenerator.nextInt(9) +1;

        textView = (TextView) findViewById(R.id.randomNumberTextView);
        textView.setText("Bitte merken Sie sich die Zahl " + zufallszahl);
        numbers.add(numbersPosition, zufallszahl);
        textResult = (TextView) findViewById(R.id.textViewResult);
    }

    public void onClickNext(View view)
    {
        Toast.makeText(getApplicationContext(), "Numberposition " + numbersPosition + " zufallszahl " + numbers.get(numbersPosition), Toast.LENGTH_SHORT).show();

        numbersPosition++;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.MESSAGE, zufallszahl);
        startActivityForResult(intent, MainActivity.REQUEST_CODE);

    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent)
    {

        if(resultCode == RESULT_OK && requestCode == MainActivity.REQUEST_CODE)
        {
            numbersPosition--;
            String message = intent.getStringExtra(MainActivity.MESSAGE);
            Toast.makeText(getApplicationContext(), "Button zahl " + message + " zufallszahl " + numbers.get(numbersPosition), Toast.LENGTH_SHORT).show();

            if(Integer.parseInt(message) == numbers.get(numbersPosition))
            {
                textResult.setText("Richtig");
            }
            else
            {

                textResult.setText("Leider Falsch");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("DISPLAY_TEXT_RESULT", textResult.getText().toString());
        outState.putString("DISPLAY_TEXT", textView.getText().toString());

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textResult.setText(savedInstanceState.getString("DISPLAY_TEXT_RESULT"));
        textView.setText(savedInstanceState.getString("DISPLAY_TEXT"));
    }

    public void onButtonClick(View view)
    {
        if(numbersPosition != 0) {
            Button button = (Button) view;
            Intent intent = getIntent();
            intent.putExtra(MainActivity.MESSAGE, button.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
