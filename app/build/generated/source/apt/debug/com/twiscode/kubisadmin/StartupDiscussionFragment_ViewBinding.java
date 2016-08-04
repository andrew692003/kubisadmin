// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin;

import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class StartupDiscussionFragment_ViewBinding<T extends StartupDiscussionFragment> implements Unbinder {
  protected T target;

  public StartupDiscussionFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.lvDiscussion = finder.findRequiredViewAsType(source, R.id.lvDiscussion, "field 'lvDiscussion'", RecyclerView.class);
    target.progressBar = finder.findRequiredViewAsType(source, R.id.progressBar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.lvDiscussion = null;
    target.progressBar = null;

    this.target = null;
  }
}
