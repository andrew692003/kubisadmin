// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin;

import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class SuggestionFragment_ViewBinding<T extends SuggestionFragment> implements Unbinder {
  protected T target;

  public SuggestionFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.listSuggest = finder.findRequiredViewAsType(source, R.id.listSuggest, "field 'listSuggest'", ListView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.listSuggest = null;

    this.target = null;
  }
}
