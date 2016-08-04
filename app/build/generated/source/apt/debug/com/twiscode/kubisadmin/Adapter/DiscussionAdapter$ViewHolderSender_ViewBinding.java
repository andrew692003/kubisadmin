// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin.Adapter;

import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.twiscode.kubisadmin.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class DiscussionAdapter$ViewHolderSender_ViewBinding<T extends DiscussionAdapter.ViewHolderSender> implements Unbinder {
  protected T target;

  public DiscussionAdapter$ViewHolderSender_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.civProfilePicture = finder.findRequiredViewAsType(source, R.id.civProfilePicture, "field 'civProfilePicture'", CircleImageView.class);
    target.tvName = finder.findRequiredViewAsType(source, R.id.tvName, "field 'tvName'", TextView.class);
    target.tvComment = finder.findRequiredViewAsType(source, R.id.tvComment, "field 'tvComment'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.civProfilePicture = null;
    target.tvName = null;
    target.tvComment = null;

    this.target = null;
  }
}
