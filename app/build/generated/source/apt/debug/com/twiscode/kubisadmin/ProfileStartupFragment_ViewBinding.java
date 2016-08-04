// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin;

import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class ProfileStartupFragment_ViewBinding<T extends ProfileStartupFragment> implements Unbinder {
  protected T target;

  public ProfileStartupFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.startupsView = finder.findRequiredViewAsType(source, R.id.my_startup_list, "field 'startupsView'", ListView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.startupsView = null;

    this.target = null;
  }
}
