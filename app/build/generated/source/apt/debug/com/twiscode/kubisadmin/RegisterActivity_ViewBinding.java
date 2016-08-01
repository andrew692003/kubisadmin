// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin;

import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class RegisterActivity_ViewBinding<T extends RegisterActivity> implements Unbinder {
  protected T target;

  public RegisterActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.civProfilePicture = finder.findRequiredViewAsType(source, R.id.civProfilePicture, "field 'civProfilePicture'", CircleImageView.class);
    target.etUsername = finder.findRequiredViewAsType(source, R.id.etUsername, "field 'etUsername'", EditText.class);
    target.etDescription = finder.findRequiredViewAsType(source, R.id.etDescription, "field 'etDescription'", EditText.class);
    target.btnDone = finder.findRequiredViewAsType(source, R.id.btnDone, "field 'btnDone'", Button.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.civProfilePicture = null;
    target.etUsername = null;
    target.etDescription = null;
    target.btnDone = null;

    this.target = null;
  }
}
