// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin;

import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class PromotedUserFragment_ViewBinding<T extends PromotedUserFragment> implements Unbinder {
  protected T target;

  public PromotedUserFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.promoteUser = finder.findRequiredViewAsType(source, R.id.promoteUser, "field 'promoteUser'", LinearLayout.class);
    target.superAdmin = finder.findRequiredViewAsType(source, R.id.superAdmin, "field 'superAdmin'", LinearLayout.class);
    target.commentator = finder.findRequiredViewAsType(source, R.id.commentator, "field 'commentator'", LinearLayout.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.promoteUser = null;
    target.superAdmin = null;
    target.commentator = null;

    this.target = null;
  }
}
