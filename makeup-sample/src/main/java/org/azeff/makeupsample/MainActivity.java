package org.azeff.makeupsample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.view.View;
import android.widget.TextView;
import org.azeff.makeup.Makeup;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView tv = viewById(R.id.makeupTextView);

    Spannable formatted = Makeup.create()
        .append("This is normal text").line()
        .append("This is bold").bold().line()
        .append("This is strikethrough").strikethrough().line()
        .append("This is italic.").italic()
        .append("This is underline, and linebreak.").underline().line()
        .append("This is red").color(Color.RED).line()
        .append("On green background").bg(Color.GREEN).line()
        .append("Bigger").proportion(2)
        .append(" Normal ")
        .append("Smaller").proportion(0.75f).line()
        .append("This is italic, bold, underline and strikethrough.")
            .italic().bold().underline().strikethrough()
        .apply();

    tv.setText(formatted);
  }

  @SuppressWarnings("unchecked")
  public <T extends View> T viewById(@IdRes int id) {
    return (T) getWindow().getDecorView().findViewById(id);
  }
}
