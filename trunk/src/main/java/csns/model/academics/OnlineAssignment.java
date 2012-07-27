/*
 * This file is part of the CSNetwork Services (CSNS) project.
 * 
 * Copyright 2012, Chengyu Sun (csun@calstatela.edu).
 * 
 * CSNS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * CSNS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with CSNS. If not, see http://www.gnu.org/licenses/agpl.html.
 */
package csns.model.academics;

import java.util.Calendar;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import csns.model.qa.QuestionSheet;

@Entity
@DiscriminatorValue("ONLINE")
public class OnlineAssignment extends Assignment {

    private static final long serialVersionUID = 1L;

    @OneToOne
    @JoinColumn(name = "question_sheet_id", unique = true)
    private QuestionSheet questionSheet;

    public OnlineAssignment()
    {
        super();

        publishDate = (Calendar) dueDate.clone();
        availableAfterDueDate = false;
        questionSheet = new QuestionSheet();
    }

    public OnlineAssignment clone()
    {
        OnlineAssignment newAssignment = new OnlineAssignment();

        newAssignment.name = name;
        newAssignment.alias = alias;
        newAssignment.totalPoints = totalPoints;
        newAssignment.section = section;
        newAssignment.questionSheet = questionSheet.clone();
        newAssignment.availableAfterDueDate = availableAfterDueDate;

        return newAssignment;
    }

    @Override
    public boolean isOnline()
    {
        return true;
    }

    public QuestionSheet getQuestionSheet()
    {
        return questionSheet;
    }

    public void setQuestionSheet( QuestionSheet questionSheet )
    {
        this.questionSheet = questionSheet;
    }

}