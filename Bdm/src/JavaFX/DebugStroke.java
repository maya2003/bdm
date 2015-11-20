/* Copyright (c) 2013, 2014, 2015 Olivier TARTROU
 * See the file COPYING for copying permission.
 *
 * https://github.com/maya2003/bdm
 */

//!!
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Control;
import javafx.scene.Group;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.beans.value.ObservableValue;


class DebugStroke extends Rectangle
{
  public DebugStroke(Control control) // Region???
  {
    super(0, 0, Color.TRANSPARENT);
    setStroke(Color.RED);
    setManaged(false);

    control.boundsInParentProperty().addListener(new ChangeListener<Bounds>()
    {
      @Override
      public void changed(final ObservableValue<? extends Bounds> observable, final Bounds oldValue, Bounds newValue)
      {
        setLayoutX(newValue.getMinX()  - 1);
        setLayoutY(newValue.getMinY()  - 1);
        setWidth( newValue.getWidth()  + 2);
        setHeight(newValue.getHeight() + 2);
      }
    });
  }

  public DebugStroke(Group group) // Region???
  {
    super(0, 0, Color.TRANSPARENT);
    setStroke(Color.RED);
    setManaged(false);

    group.boundsInParentProperty().addListener(new ChangeListener<Bounds>()
    {
      @Override
      public void changed(final ObservableValue<? extends Bounds> observable, final Bounds oldValue, Bounds newValue)
      {
        setLayoutX(newValue.getMinX()  - 1);
        setLayoutY(newValue.getMinY()  - 1);
        setWidth( newValue.getWidth()  + 2);
        setHeight(newValue.getHeight() + 2);
      }
    });
  }

}

