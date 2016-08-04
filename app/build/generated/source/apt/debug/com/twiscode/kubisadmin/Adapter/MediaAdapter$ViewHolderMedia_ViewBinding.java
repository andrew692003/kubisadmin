// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin.Adapter;

import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.twiscode.kubisadmin.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class MediaAdapter$ViewHolderMedia_ViewBinding<T extends MediaAdapter.ViewHolderMedia> implements Unbinder {
  protected T target;

  public MediaAdapter$ViewHolderMedia_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.imageView = finder.findRequiredViewAsType(source, R.id.imageView, "field 'imageView'", ImageView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.imageView = null;

    this.target = null;
  }
}
