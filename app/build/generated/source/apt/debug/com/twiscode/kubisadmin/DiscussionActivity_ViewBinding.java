// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class DiscussionActivity_ViewBinding<T extends DiscussionActivity> implements Unbinder {
  protected T target;

  public DiscussionActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.lvDiscussion = finder.findRequiredViewAsType(source, R.id.lvDiscussion, "field 'lvDiscussion'", RecyclerView.class);
    target.etComment = finder.findRequiredViewAsType(source, R.id.etComment, "field 'etComment'", EditText.class);
    target.btnWrite = finder.findRequiredViewAsType(source, R.id.btnWrite, "field 'btnWrite'", Button.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.lvDiscussion = null;
    target.etComment = null;
    target.btnWrite = null;

    this.target = null;
  }
}
