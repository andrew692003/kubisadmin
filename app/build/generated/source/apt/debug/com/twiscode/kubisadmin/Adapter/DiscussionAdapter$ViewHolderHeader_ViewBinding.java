// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin.Adapter;

import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.twiscode.kubisadmin.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class DiscussionAdapter$ViewHolderHeader_ViewBinding<T extends DiscussionAdapter.ViewHolderHeader> implements Unbinder {
  protected T target;

  public DiscussionAdapter$ViewHolderHeader_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.headerText = finder.findRequiredViewAsType(source, R.id.headerText, "field 'headerText'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.headerText = null;

    this.target = null;
  }
}
