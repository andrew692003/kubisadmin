// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin;

import android.support.design.widget.FloatingActionButton;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class SuggestionDetailActivity_ViewBinding<T extends SuggestionDetailActivity> implements Unbinder {
  protected T target;

  public SuggestionDetailActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.btnAcc = finder.findRequiredViewAsType(source, R.id.accept, "field 'btnAcc'", FloatingActionButton.class);
    target.btnDec = finder.findRequiredViewAsType(source, R.id.decline, "field 'btnDec'", FloatingActionButton.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.btnAcc = null;
    target.btnDec = null;

    this.target = null;
  }
}
