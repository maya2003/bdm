/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

//!!
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Text;

//!! private API!
import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;


class TextArea extends javafx.scene.control.TextArea
{
  protected final double TEXT_MARGIN = 20;
  protected static FontMetrics fontMetrics;

  public TextArea()
  {
    textProperty().addListener(new ChangeListener<String>()
    {
      @Override
      public void changed(ObservableValue observable, String oldTextString, String newTextString)
      {
        Text text = (Text)lookup(".text");
        if(text != null)
        {
          fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(text.getFont()); // cache
          setPrefWidth(fontMetrics.computeStringWidth(newTextString) + TEXT_MARGIN);
        }
      }
    });
  }

}

