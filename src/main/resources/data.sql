SELECT * FROM admin.role_table;

DROP table public.leave_table;
DROP table public.notification_table;

create table public.leave_table
(
    leave_id           integer not null
        constraint leave_table_pkey
            primary key,
    authorisation_date timestamp,
    authorised_user    varchar(255),
    created_date       timestamp,
    created_user       varchar(255),
    modified_date      timestamp,
    modified_user      varchar(255),
    transaction_status varchar(255),
    approver_comments  varchar(255),
    approver_id        varchar(255),
    contact_address    varchar(1000),
    contact_phone      varchar(255),
    delete_flag        boolean,
    description        varchar(255),
    end_date           timestamp,
    leave_name         varchar(255),
    number_of_days     integer,
    pay_percentage     integer,
    resource_id        varchar(255),
    start_date         timestamp,
    status             varchar(255),
    tickets_paid       boolean,
    tickets_to         varchar(255),
    COLLEAGUE_NAME     varchar(255),
    COLLEAGUE_EMAIL		varchar(255),
    COLLEAGUE_CONTACT  integer,
    notes  			   varchar(2000)
);

alter table public.leave_table
    owner to postgres;

--delete from admin.resource_table;

--INSERT INTO admin.resource_table (uuid, birth_date, email_address, first_name, joining_date, last_name, active_status) VALUES ('d1a3504c-6b90-48ec-b4ab-a6cce972d01c', '1980-09-23 10:41:14.000000', 'ravi@chinasystems-me.com', 'ravikanth', '2005-09-23 10:41:31.000000', 'mamillapalli', true);

--INSERT INTO admin.resource_table (uuid, birth_date, email_address, first_name, joining_date, last_name, active_status) VALUES ('d1a3504c-6b90-48ec-b4ab-a6cce972d01c', '1980-09-23 10:41:14.000000', 'ravi@chinasystems-me.com', 'ravikanth', '2005-09-23 10:41:31.000000', 'mamillapalli', true);

    
create table public.notification_table
(
    message_id              uuid not null
        constraint notification_table_pkey
            primary key,
    authorisation_date      timestamp,
    authorised_user         varchar(255),
    created_date            timestamp,
    created_user            varchar(255),
    modified_date           timestamp,
    modified_user           varchar(255),
    transaction_status      varchar(255),
    attachments             bytea,
    bcc_list                varchar(255),
    cc_list                 varchar(255),
    content                 text,
    from_list               varchar(255),
    notification_event      text,
    status                  varchar(255),
    subject                 varchar(255),
    to_list                 varchar(255),
    transaction_information text
);

alter table public.notification_table
    owner to postgres;