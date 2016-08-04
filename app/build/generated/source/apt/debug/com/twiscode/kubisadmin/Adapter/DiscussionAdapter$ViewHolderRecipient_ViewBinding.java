// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin.Adapter;

import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.twiscode.kubisadmin.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class DiscussionAdapter$ViewHolderRecipient_ViewBinding<T extends DiscussionAdapter.ViewHolderRecipient> implements Unbinder {
  protected T target;

  public DiscussionAdapter$ViewHolderRecipient_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.tvName = finder.findRequiredViewAsType(source, R.id.tvName, "field 'tvName'", TextView.class);
    target.tvComment = finder.findRequiredViewAsType(source, R.id.tvComment, "field 'tvComment'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tvName = null;
    target.tvComment = null;

    this.target = null;
  }
}
