-- This script creates all the tables/indexes/procedures/triggers and some
-- data required for an initial CSNS setup.

set client_min_messages=WARNING;

create sequence hibernate_sequence minvalue 2000000;

---------------------
-- users and roles --
---------------------

create table users (
    id                  bigint primary key,
    cin                 varchar(255) not null unique,
    username            varchar(255) not null unique,
    password            varchar(255) not null,
    last_name           varchar(255) not null,
    first_name          varchar(255) not null,
    middle_name         varchar(255),
    gender              char(1) check( gender = 'M' or gender = 'F' ),
    birthday            date,
    street              varchar(255),
    city                varchar(255),
    state               varchar(255),
    zip                 varchar(255),
    primary_email       varchar(255) not null unique,
    secondary_email     varchar(255),
    cell_phone          varchar(255),
    home_phone          varchar(255),
    office_phone        varchar(255),
    enabled             boolean not null default 't',
    temporary           boolean not null default 'f',
    num_of_forum_posts  integer not null default 0,
    disk_quota          integer not null default 200
);

create table authorities (
    user_id bigint not null references users(id),
    role    varchar(255)
);

insert into users (id, cin, username, password, last_name, first_name, primary_email) values
    (1000, '1000', 'sysadmin', md5('abcd'), 'System', 'Administrator', 'csnsadmin@localhost.localdomain');

insert into authorities (user_id, role) values (1000, 'ROLE_ADMIN');

-- for remember-me service
create table persistent_logins (
    series      varchar(64) primary key,
    username    varchar(64) not null,
    token       varchar(64) not null,
    last_used   timestamp not null
);

-------------------------
-- files and resources --
-------------------------

create table files (
    id              bigint primary key,
    name            varchar(255) not null,
    type            varchar(255),
    size            bigint,
    date            timestamp not null default current_timestamp,
    owner_id        bigint not null references users(id),
    parent_id       bigint references files(id),
    folder          boolean not null default 'f',
    public          boolean not null default 'f',
    submission_id   bigint,
    regular         boolean not null default 'f',
    deleted         boolean not null default 'f'
);

create table resources (
    id      bigint primary key,
    name    varchar(255),
    type    integer not null,
    text    text,
    file_id bigint references files(id),
    url     varchar(2000)
);

-------------------
-- subscriptions --
-------------------

create table subscriptions (
    id                  bigint primary key,
    subscribable_type   char(2) not null,
    subscribable_id     bigint not null,
    subscriber_id       bigint not null references users(id),
    date                timestamp not null default current_timestamp,
    notification_sent   boolean not null default 'f',
    auto_subscribed     boolean not null default 'f',
  unique(subscribable_type, subscribable_id, subscriber_id)
);

--------------------------
-- question and answers --
--------------------------

create table question_sheets (
    id          bigint primary key,
    description varchar(8000)
);

create table question_sections (
    id                  bigint primary key,
    description         varchar(8000),
    question_sheet_id   bigint references question_sheets(id),
    section_index       integer,
  unique (question_sheet_id, section_index)
);

create table questions (
    id                  bigint primary key,
    question_type       varchar(255) not null,
    description         varchar(8000),
    point_value         integer not null default 1,
    min_selections      integer,
    max_selections      integer,
    min_rating          integer,
    max_rating          integer,
    text_length         integer,
    attachment_allowed  boolean not null default 'f',
    correct_answer      varchar(8000),
    question_section_id bigint references question_sections(id),
    question_index      integer,
  unique (question_section_id, question_index)
);

create table question_choices (
    question_id     bigint not null references questions(id),
    choice          varchar(4000),
    choice_index    integer not null,
  primary key (question_id, choice_index)
);

create table question_correct_selections (
    question_id bigint not null references questions(id),
    selection   integer,
  primary key (question_id, selection)
);

create table answer_sheets (
    id                  bigint primary key,
    question_sheet_id   bigint not null references question_sheets(id),
    author_id           bigint references users(id),
    date                timestamp
);

create table answer_sections (
    id              bigint primary key,
    answer_sheet_id bigint not null references answer_sheets(id),
    section_index   integer not null,
  unique (answer_sheet_id, section_index)
);

create table answers (
    id                  bigint primary key,
    answer_type         varchar(255) not null,
    answer_section_id   bigint not null references answer_sections(id),
    answer_index        integer not null,
    question_id         bigint references questions(id),
    rating              integer,
    text                varchar(8000),
    attachment_id       bigint unique references files(id),
  unique (answer_section_id, answer_index)
);

create table answer_selections (
    answer_id   bigint not null references answers(id),
    selection   integer
);

------------
-- grades --
------------

create table grades (
    id          bigint primary key,
    symbol      varchar(255) not null unique,
    value       double precision,
    description varchar(1000)
);

-- The following grades are defined in the Faculty Handbook
-- (http://www.calstatela.edu/academic/senate/handbook/) under the
-- chpater Instructional Policies.
  
insert into grades (id, symbol, value, description) values
    (2000, 'A', 4.0, 'Superior Attainment of Course Objectives');
insert into grades (id, symbol, value, description) values
    (2002, 'A-', 3.7, 'Outstanding Attainment of Course Objectives');
insert into grades (id, symbol, value, description) values
    (2004, 'B+', 3.3, 'Very Good Attainment of Course Objectives');
insert into grades (id, symbol, value, description) values
    (2006, 'B', 3.0, 'Good Attainment of Course Objectives');
insert into grades (id, symbol, value, description) values
    (2008, 'B-', 2.7, 'Fairly Good Attainment of Course  Objectives');
insert into grades (id, symbol, value, description) values
    (2010, 'C+', 2.3, 'Above Average Attainment of Course Objectives');
insert into grades (id, symbol, value, description) values
    (2012, 'C', 2.0, 'Average Attainment of Course Objectives');
insert into grades (id, symbol, value, description) values
    (2014, 'C-', 1.7, 'Below Average Attainment of Course Objectives');
insert into grades (id, symbol, value, description) values
    (2016, 'D+', 1.3, 'Weak Attainment of Course Objectives');
insert into grades (id, symbol, value, description) values
    (2018, 'D', 1.0, 'Poor Attainment of Course Objectives');
insert into grades (id, symbol, value, description) values
    (2020, 'D-', 0.7, 'Barely Passing Attainment of Course Objectives');
insert into grades (id, symbol, value, description) values
    (2022, 'F', 0.0, 'Non-Attainment of Course Objectives');
insert into grades (id, symbol, description) values
    (2024, 'CR', 'Credit');
insert into grades (id, symbol, description) values
    (2026, 'NC', 'No Credit');
insert into grades (id, symbol, description) values
    (2028, 'RP', 'Report in Progress');
insert into grades (id, symbol, description) values
    (2030, 'RD', 'Report Delayed');
insert into grades (id, symbol, description) values
    (2032, 'I', 'Incomplete  Authorized');
insert into grades (id, symbol, value, description) values
    (2034, 'IC', 0.0, 'Incompleted Charged');
insert into grades (id, symbol, description) values
    (2036, 'W', 'Withdraw');
insert into grades (id, symbol, value, description) values
    (2038, 'WU', 0.0, 'Withdraw Unauthorized');

----------------------------------------
-- courses, sections, and enrollments --
----------------------------------------

create table courses (
    id              bigint primary key,
    code            varchar(255) not null unique,
    name            varchar(255) not null,
    min_units       integer not null default 4,
    max_units       integer not null default 4,
    coordinator_id  bigint references users(id),
    syllabus_id     bigint references files(id),
    obsolete        boolean not null default 'f'
);

alter table courses add column tsv tsvector;

create function courses_ts_trigger_function() returns trigger as $$
begin
    new.tsv := setweight(to_tsvector(new.code), 'A') ||
               setweight(to_tsvector(new.name), 'B');
    return new;
end
$$ language plpgsql;

create trigger courses_ts_trigger
    before insert or update on courses
    for each row execute procedure courses_ts_trigger_function();

create index courses_ts_index on courses using gin(tsv);

create table sections (
    id              bigint primary key,
    quarter         integer not null,
    course_id       bigint not null references courses(id),
    number          integer not null default 1,
  unique (quarter, course_id, number)
);

create table section_instructors (
    section_id          bigint not null references sections(id),
    instructor_id       bigint not null references users(id),
    instructor_order    integer not null,
  primary key (section_id, instructor_order)
);

create table enrollments (
    id              bigint primary key,
    section_id      bigint not null references sections(id),
    student_id      bigint not null references users(id),
    grade_id        bigint references grades(id),
    comments        text,
    grade_mailed    boolean not null default 'f',
  unique (section_id, student_id)
);

--------------------------------
-- assignments and submission --
--------------------------------

create table assignments (
    id                          bigint primary key,
    assignment_type             varchar(255) not null default 'REGULAR',
    name                        varchar(255) not null,
    alias                       varchar(255) not null,
    total_points                varchar(255),
    section_id                  bigint references sections(id),
    publish_date                timestamp,
    due_date                    timestamp,
    max_file_size               bigint,
    file_extensions             varchar(255),
    available_after_due_date    boolean not null default 't',
    question_sheet_id           bigint unique references question_sheets(id)
);

alter table assignments add column tsv tsvector;

create function assignments_ts_trigger_function() returns trigger as $$
declare
    l_quarter       varchar;
    l_course_code   varchar;
begin
    if new.section_id is not null then
        select quarter(quarter) into l_quarter from sections
            where id = new.section_id;
        select c.code into l_course_code from sections s, courses c
            where s.id = new.section_id and c.id = s.course_id;
    end if;
    new.tsv := setweight(to_tsvector(l_quarter), 'A') ||
               setweight(to_tsvector(l_course_code), 'A') ||
               setweight(to_tsvector(new.name), 'A');
    return new;
end
$$ language plpgsql;

create trigger assignments_ts_trigger
    before insert or update on assignments
    for each row execute procedure assignments_ts_trigger_function();

create index assignments_ts_index on assignments using gin(tsv);

create table submissions (
    id              bigint primary key,
    submission_type varchar(255) not null default 'REGULAR',
    student_id      bigint not null references users(id),
    assignment_id   bigint not null references assignments(id),
    due_date        timestamp,
    grade           varchar(255),
    comments        text,
    grade_mailed    boolean not null default 'f',
    answer_sheet_id bigint unique references answer_sheets(id),
    saved           boolean not null default 'f',
    finished        boolean not null default 'f',
  unique (student_id, assignment_id)
);

alter table files add constraint files_submission_id_fkey
    foreign key (submission_id) references submissions(id);

-----------------
-- departments --
-----------------

create table departments (
    id              bigint primary key,
    name            varchar(255) not null unique,
    abbreviation    varchar(255) not null unique,
    welcome_message varchar(8000)
);

create table department_administrators (
    department_id   bigint not null references departments(id),
    user_id         bigint not null references users(id)
);

create table department_faculty (
    department_id   bigint not null references departments(id),
    user_id         bigint not null references users(id)
);

create table department_instructors (
    department_id   bigint not null references departments(id),
    user_id         bigint not null references users(id)
);

create table department_reviewers (
    department_id   bigint not null references departments(id),
    user_id         bigint not null references users(id)
);

create table department_undergraduate_courses (
    department_id   bigint not null references departments(id),
    course_id       bigint not null references courses(id)
);
    
create table department_additional_undergraduate_courses (
    department_id   bigint not null references departments(id),
    course_id       bigint not null references courses(id)
);

create table department_graduate_courses (
    department_id   bigint not null references departments(id),
    course_id       bigint not null references courses(id)
);

create table department_additional_graduate_courses (
    department_id   bigint not null references departments(id),
    course_id       bigint not null references courses(id)
);

-------------
-- surveys --
-------------

create table surveys (
    id                  bigint primary key,
    name                varchar(255) not null,
    type                varchar(255) default 'ANONYMOUS',
    question_sheet_id   bigint not null unique references question_sheets(id),
    publish_date        timestamp,
    close_date          timestamp,
    department_id       bigint references departments(id),
    author_id           bigint not null references users(id),
    date                timestamp not null default current_timestamp,
    deleted             boolean not null default 'f'
);

create table survey_responses (
    id              bigint primary key,
    survey_id       bigint not null references surveys(id),
    answer_sheet_id bigint not null unique references answer_sheets(id)
);

create table surveys_taken (
    user_id     bigint not null references users(id),
    survey_id   bigint not null references surveys(id),
  primary key (user_id, survey_id)
);

------------
-- forums --
------------

create table forums (
    id              bigint primary key,
    name            varchar(80) not null,
    description     varchar(500),
    date            timestamp default current_timestamp,
    num_of_topics   integer not null default 0,
    num_of_posts    integer not null default 0,
    last_post_id    bigint unique,
    department_id   bigint references departments(id),
    course_id       bigint unique references courses(id),
    hidden          boolean not null default 'f',
  check ( department_id is null or course_id is null )
);

alter table forums add column tsv tsvector;

create function forums_ts_trigger_function() returns trigger as $$
declare
    l_course        courses;
    l_department    departments;
begin
    if new.course_id is not null then
        select * into l_course from courses where id = new.course_id;
        new.tsv := setweight(to_tsvector(l_course.code), 'A') ||
                   setweight(to_tsvector(l_course.name), 'A');
    elsif new.department_id is not null then
        select * into l_department from departments where id = new.department_id;
        new.tsv := setweight(to_tsvector(new.name), 'A') ||
                   setweight(to_tsvector(l_department.name), 'B') ||
                   setweight(to_tsvector(l_department.abbreviation), 'B');
    else
        new.tsv := setweight(to_tsvector(new.name), 'A');
    end if;
    return new;
end
$$ language plpgsql;

create trigger forums_ts_trigger
    before insert or update on forums
    for each row execute procedure forums_ts_trigger_function();

create index forums_ts_index on forums using gin(tsv);

insert into forums (id, name, description, hidden) values
    (3000, 'CSNS', 'All things related to CSNS.', 'f');
insert into forums (id, name, description, hidden) values
    (3001, 'Wiki Discussion', 'Discussion of wiki pages.', 't');

create table forum_moderators (
    forum_id    bigint not null references forums(id),
    user_id     bigint not null references users(id),
  primary key (forum_id, user_id)
);

create table forum_topics (
    id              bigint primary key,
    pinned          boolean not null default 'f',
    num_of_views    integer not null default 0,
    first_post_id   bigint,
    last_post_id    bigint,
    forum_id        bigint references forums(id),
    deleted         boolean not null default 'f'
);

create table forum_posts (
    id          bigint primary key,
    subject     varchar(255) not null,
    content     text not null,
    author_id   bigint references users(id),
    date        timestamp default current_timestamp,
    topic_id    bigint references forum_topics(id),
    edited_by   bigint references users(id),
    edit_date   timestamp
);

create table forum_post_attachments (
    post_id bigint not null references forum_posts(id),
    file_id bigint not null references files(id),
  primary key (post_id, file_id)
);

alter table forums add constraint fk_forum_last_post
    foreign key (last_post_id) references forum_posts(id);
alter table forum_topics add constraint fk_forum_topic_first_post
    foreign key (first_post_id) references forum_posts(id);
alter table forum_topics add constraint fk_forum_topic_last_post 
    foreign key (last_post_id) references forum_posts(id);

alter table forum_posts add column tsv tsvector;

create function forum_posts_ts_trigger_function() returns trigger as $$
begin
    new.tsv := setweight(to_tsvector(new.subject), 'A') ||
               setweight(to_tsvector(new.content), 'D');
    return new;
end
$$ language plpgsql;

create trigger forum_posts_ts_trigger
    before insert or update on forum_posts
    for each row execute procedure forum_posts_ts_trigger_function();

create index forum_posts_ts_index on forum_posts using gin(tsv);

----------
-- wiki --
----------

create table wiki_pages (
    id          bigint primary key,
    path        varchar(1000) not null unique,
    owner_id    bigint not null references users(id),
    views       integer not null default 0,
    password    varchar(255),
    locked      boolean not null default 'f'
);

create index wiki_pages_path_pattern_index on wiki_pages (path varchar_pattern_ops);

create table wiki_revisions (
    id              bigint primary key,
    subject         varchar(1000) not null,
    content         text not null,
    date            timestamp default current_timestamp,
    author_id       bigint not null references users(id),
    page_id         bigint not null references wiki_pages(id),
    include_sidebar boolean not null default 'f'
);

create table wiki_revision_attachments (
    revision_id bigint not null references wiki_revisions(id),
    file_id     bigint not null references files(id),
  primary key (revision_id, file_id)
);

create table wiki_discussions (
    page_id     bigint not null references wiki_pages(id),
    topic_id    bigint not null references forum_topics(id),
  primary key (page_id, topic_id)
);

alter table wiki_pages add column tsv tsvector;
alter table wiki_pages add column ts_subject varchar(1000);
alter table wiki_pages add column ts_content text;

create function wiki_revisions_ts_trigger_function() returns trigger as $$
begin
    update wiki_pages
        set tsv = setweight(to_tsvector(new.subject), 'A') || setweight(to_tsvector(new.content), 'D'),
            ts_subject = new.subject,
            ts_content = new.content
        where id = new.page_id;
    return new;
end
$$ language plpgsql;

create trigger wiki_revisions_ts_trigger
    before insert on wiki_revisions
    for each row execute procedure wiki_revisions_ts_trigger_function();

create index wiki_pages_ts_index on wiki_pages using gin(tsv);

----------
-- news --
----------

create table news (
    id              bigint primary key,
    department_id   bigint references departments(id),
    topic_id        bigint not null references forum_topics(id),
    expire_date     timestamp
);

------------------
-- mailinglists --
------------------

create table mailinglists (
    id              bigint primary key,
    name            varchar(255) not null,
    description     varchar(4092),
    department_id   bigint references departments(id),
  unique (department_id, name)
);

create table mailinglist_messages (
    id              bigint primary key,
    subject         varchar(255) not null,
    content         varchar(8092) not null,
    date            timestamp default current_timestamp,
    author_id       bigint not null references users(id),
    mailinglist_id  bigint references mailinglists(id)
);

create table mailinglist_message_attachments (
    message_id  bigint not null references mailinglist_messages(id),
    file_id     bigint not null references files(id),
  primary key (message_id, file_id)
);

alter table mailinglist_messages add column tsv tsvector;

create function mailinglist_messages_ts_trigger_function() returns trigger as $$
declare
    author users%rowtype;
begin
	select * into author from users where id = new.author_id;
    new.tsv := setweight(to_tsvector(author.first_name), 'A')
        || setweight(to_tsvector(author.first_name), 'A')
        || setweight(to_tsvector(new.subject), 'A')
        || setweight(to_tsvector(new.content), 'D');
    return new;
end
$$ language plpgsql;

create trigger mailinglist_messages_ts_trigger
    before insert or update on mailinglist_messages
    for each row execute procedure mailinglist_messages_ts_trigger_function();

create index mailinglist_messages_ts_index on mailinglist_messages using gin(tsv);

---------------
-- standings --
---------------

create table standings (
    id          bigint primary key,
    symbol      varchar(255) not null unique,
    name        varchar(255),
    description varchar(8000)
);

insert into standings (id, symbol, name, description) values 
    (4000, 'B', 'Undergraduate',
      'Undergraduate student.');
insert into standings (id, symbol, name, description) values 
    (4002, 'BC','Grad Check for a BS/BA Degree',
      'Undergradudate students who have requested grad check.');
insert into standings (id, symbol, name, description) values 
    (4004, 'BG','Graduated with BS/BA Degree',
      'Graduated with a Bachelor''s Degree.');
insert into standings (id, symbol, name, description) values 
    (4010, 'G0', 'Incoming Graduate Student',
      'Incoming graduate student.');
insert into standings (id, symbol, name, description) values 
    (4012, 'G1', 'Conditionaly Classified Graduate',
      'Graduate students who have not completed all prerequisite courses.');
insert into standings (id, symbol, name, description) values 
    (4014, 'G2', 'Classified Graduate',
      'Graduate students who have completed all prerequiste courses but ' ||
      'have not yet fullfilled the requirements for Candidacy.');
insert into standings (id, symbol, name, description) values 
    (4016, 'G3', 'Candidacy Graduate',
      'Graduate students who have met the Candidacy requirements, which ' ||
      'include completion of all core courses, completion of at least half ' ||
      ' of the remaining course requirements, passing WPE, and declaring a ' ||
      ' thesis/project or comprehensive exam option.');
insert into standings (id, symbol, name, description) values 
    (4018, 'GC', 'Grad Check for an MS Degree',
      'Gradudate students who have requested grad check.');
insert into standings (id, symbol, name, description) values 
    (4020, 'GG', 'Graduated with MS Degree',
      'Graduated with an Master''s Degree.');
insert into standings (id, symbol, name, description) values 
    (4030, 'NG', 'Not Graduated',
      'The student did not graduate for some reason. For example, ' ||
      'the student was disqualified from the program, or dropped out of ' ||
      'the program, or simply stopped taking classes.');

create table standing_mailinglists (
    standing_id     bigint not null references standings(id),
    mailinglist     varchar(255)
);

-- mailing list membership for B Standing
insert into standing_mailinglists values (4000, 'students');
insert into standing_mailinglists values (4000, 'undergrads');

-- mailing list membership for BG Standing
insert into standing_mailinglists values (4004, 'alumni');
insert into standing_mailinglists values (4004, 'alumni-undergrad');

-- mailing list membership for G0 Standing
insert into standing_mailinglists values (4010, 'students');
insert into standing_mailinglists values (4010, 'grads');
insert into standing_mailinglists values (4010, 'grads-g0');

-- mailing list membership for G1 Standing
insert into standing_mailinglists values (4012, 'students');
insert into standing_mailinglists values (4012, 'grads');
insert into standing_mailinglists values (4012, 'grads-g1');

-- mailing list membership for G2 Standing
insert into standing_mailinglists values (4014, 'students');
insert into standing_mailinglists values (4014, 'grads');
insert into standing_mailinglists values (4014, 'grads-g2');

-- Mailing list membership for G3 Standing
insert into standing_mailinglists values (4016, 'students');
insert into standing_mailinglists values (4016, 'grads');
insert into standing_mailinglists values (4016, 'grads-g3');

-- mailing list membership for GG Standing
insert into standing_mailinglists values (4020, 'alumni');
insert into standing_mailinglists values (4020, 'alumni-grad');

create table academic_standings (
    id              bigint primary key,
    student_id      bigint references users(id),
    department_id   bigint references departments(id),
    standing_id     bigint references standings(id),
    quarter         integer,
  unique (student_id, department_id, standing_id)
);

create table current_standings (
    student_id              bigint not null references users(id),
    department_id           bigint not null references departments(id),
    academic_standing_id    bigint unique not null references academic_standings(id),
  primary key (student_id, department_id)
);

create or replace function mailinglist_subscription_on_standing_change_trigger_function()
    returns trigger as $$
declare
    l_dept              varchar;
    l_mailinglist       varchar;
    l_standing_id       standings.id%type;
    l_mailinglist_id    mailinglists.id%type;
    l_subscription_id   subscriptions.id%type;
begin
    if tg_op = 'DELETE' or tg_op = 'UPDATE' then
        select abbreviation into l_dept from departments
            where id = old.department_id;
        select standing_id into l_standing_id from academic_standings
            where id = old.academic_standing_id;
        for l_mailinglist in select mailinglist from standing_mailinglists
            where standing_id = l_standing_id loop
                delete from subscriptions where subscriber_id = old.student_id
                    and subscribable_type = 'ML' and auto_subscribed = 't'
                    and subscribable_id = (select id from mailinglists
                        where name = l_dept || '-' || l_mailinglist);
        end loop;
	end if;
	
    if tg_op = 'INSERT' or tg_op = 'UPDATE' then
        select abbreviation into l_dept from departments
            where id = new.department_id;
        select standing_id into l_standing_id from academic_standings
            where id = new.academic_standing_id;
        for l_mailinglist in select mailinglist from standing_mailinglists
            where standing_id = l_standing_id loop
            select id into l_mailinglist_id from mailinglists
                where name = l_dept || '-' || l_mailinglist;
            select id into l_subscription_id from subscriptions
                where subscribable_id = l_mailinglist_id
                and subscriber_id = new.student_id
                and subscribable_type = 'ML';
            if not found then
                insert into subscriptions (id, subscribable_type, subscribable_id,
                    subscriber_id, auto_subscribed) values (
                    nextval('hibernate_sequence'), 'ML', l_mailinglist_id,
                    new.student_id, 't');
            end if;
        end loop;
    end if;

    return null; 
end
$$ language plpgsql;

create trigger mailinglist_subscription_on_standing_change_trigger
    after insert or update or delete on current_standings
    for each row execute procedure mailinglist_subscription_on_standing_change_trigger_function();

----------------
-- advisement --
----------------

create table advisement_records (
    id                  bigint primary key,
    student_id          bigint references users(id),
    advisor_id          bigint references users(id),
    comment             varchar(8000),
    date                timestamp default current_timestamp,
    editable            boolean not null default 't',
    for_advisors_only   boolean not null default 'f',
    emailed_to_students boolean not null default 'f',
    deleted             boolean not null default 'f'
);

create table advisement_record_attachments (
    record_id   bigint not null references advisement_records(id),
    file_id     bigint not null references files(id),
  primary key (record_id, file_id)
);

create table course_substitutions (
    id                      bigint primary key,
    original_course_id      bigint references courses(id),
    substitute_course_id    bigint references courses(id),
    advisement_record_id    bigint references advisement_records(id)
);

create table course_transfers (
    id                      bigint primary key,
    course_id               bigint references courses(id),
    advisement_record_id    bigint references advisement_records(id)
);

create table course_waivers (
    id                      bigint primary key,
    course_id               bigint references courses(id),
    advisement_record_id    bigint references advisement_records(id)
);

--------------
-- projects --
--------------

create table projects (
    id              bigint primary key,
    name            varchar(255) not null,
    description     varchar(8000),
    department_id   bigint references departments(id),
    year            integer not null,
    published       boolean not null default 'f'
);

create table project_advisors (
    project_id      bigint not null references projects(id),
    advisor_id      bigint not null references users(id),
    advisor_order   bigint not null,
  primary key (project_id, advisor_order)
);

create table project_members (
    project_id  bigint not null,
    member_id   bigint not null,
  primary key (project_id, member_id)
);

create table project_resources (
    project_id      bigint not null references projects(id),
    resource_id     bigint not null references resources(id),
    resource_order  bigint not null,
  primary key (project_id, resource_order)
);

------------------------------
-- functions and procedures --
------------------------------

--
-- Given a date, returns the quarter.
--
create or replace function quarter( p_date date ) returns integer as $$
declare
    l_code integer := (extract(year from p_date) - 1900) * 10;
    l_week integer := extract(week from p_date);
begin
    if l_week < 13 then
        l_code := l_code + 1;
    elsif l_week < 25 then
        l_code := l_code + 3;
    elsif l_week < 38 then
        l_code := l_code + 6;
    else
        l_code := l_code + 9;
    end if;
    return l_code;
end;
$$ language plpgsql;

--
-- Returns the current quarter.
--
create or replace function quarter() returns integer as $$
begin
    return quarter(current_date);
end;
$$ language plpgsql;

--
-- Given a quarter code, returns the quarter name (e.g. Fall 2012).
--
create or replace function quarter( p_code integer ) returns varchar as $$
declare
    l_year      varchar;
    l_quarter   varchar;
begin
    l_year := cast( p_code/10+1900 as varchar );

    case p_code % 10
        when 1 then
            l_quarter = 'Winter';
        when 3 then
            l_quarter = 'Spring';
        when 6 then
            l_quarter = 'Summer';
        else
            l_quarter = 'Fall';
    end case;

    return l_quarter || ' ' || l_year;
end;
$$ language plpgsql;
