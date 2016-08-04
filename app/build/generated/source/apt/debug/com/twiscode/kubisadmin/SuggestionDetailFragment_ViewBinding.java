// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class SuggestionDetailFragment_ViewBinding<T extends SuggestionDetailFragment> implements Unbinder {
  protected T target;

  public SuggestionDetailFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.descSuggest = finder.findRequiredViewAsType(source, R.id.suggestDesc, "field 'descSuggest'", TextView.class);
    target.imageNominate = finder.findRequiredViewAsType(source, R.id.nominateImage, "field 'imageNominate'", ImageView.class);
    target.nameNominate = finder.findRequiredViewAsType(source, R.id.nominateName, "field 'nameNominate'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.descSuggest = null;
    target.imageNominate = null;
    target.nameNominate = null;

    this.target = null;
  }
}
