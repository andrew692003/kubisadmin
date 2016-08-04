// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin;

import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.zhy.view.flowlayout.TagFlowLayout;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class StartupInfoFragment_ViewBinding<T extends StartupInfoFragment> implements Unbinder {
  protected T target;

  public StartupInfoFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.gridFounder = finder.findRequiredViewAsType(source, R.id.gridFounder, "field 'gridFounder'", ExpandableHeightGridView.class);
    target.flowTags = finder.findRequiredViewAsType(source, R.id.flowTags, "field 'flowTags'", TagFlowLayout.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.gridFounder = null;
    target.flowTags = null;

    this.target = null;
  }
}
