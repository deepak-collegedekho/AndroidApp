package com.collegedekho.app.listener;

import com.collegedekho.app.entities.InstituteCourse;

/**
 * @author Mayank Gautam
 *         Created: 09/07/15
 */
public interface OnApplyClickedListener {
    void onCourseApplied(InstituteCourse id);
    void displayMessage(int messageId);
}
