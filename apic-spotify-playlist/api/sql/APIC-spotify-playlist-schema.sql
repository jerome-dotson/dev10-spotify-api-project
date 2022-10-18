drop database if exists APIc_spotify_playlist;
create database APIc_spotify_playlist;
use APIc_spotify_playlist;

create table app_user(
	app_user_id 	int primary key auto_increment,
    first_name		varchar(100) not null,
    last_name		varchar(100) not null,
    username		varchar(100) not null unique,
    password_hash	char(100) not null,
    email			varchar(200) not null unique,
    disabled		boolean default 0
);

create table app_role(
	app_role_id		int primary key auto_increment,
    `name`			varchar(50) not null
);

create table playlist(
	playlist_id		int primary key auto_increment,
    `name`			varchar(100) not null,
    `description`	varchar(1000),
    
    owner_id		int not null,
    constraint fk_playlist_user foreign key (owner_id) references app_user(app_user_id)
);

create table track(
	track_id		int primary key auto_increment,
    `name`			varchar(250) not null,
    duration_ms		bigint not null,
    artist			varchar(250) not null
);

create table track_playlist(
	track_id		int not null,
    playlist_id		int not null,
    
    app_user_id		int not null,
    constraint fk_user_track_playlist foreign key (app_user_id) references app_user(app_user_id),
    
    constraint pk_track_playlist primary key (track_id, playlist_id),
    constraint fk_track_playlist_track foreign key (track_id) references track(track_id),
    constraint fk_track_playlist_playlist foreign key (playlist_id) references playlist(playlist_id)
);

create table tag(
	tag_id 			int primary key auto_increment,
    content			varchar(250) not null,
    
    app_user_id		int not null,
    constraint fk_tag_user foreign key (app_user_id) references app_user(app_user_id)
);

create table tag_playlist(
	tag_id			int not null,
    playlist_id		int not null,
    
    constraint pk_tag_playlist primary key (tag_id, playlist_id),
    constraint fk_tag_playlist_tag foreign key (tag_id) references tag(tag_id),
    constraint fk_tag_playlist_playlist foreign key (playlist_id) references playlist(playlist_id)
);

create table user_role(
	app_user_id		int not null,
    app_role_id			int not null,
    
    constraint pk_user_role primary key (app_user_id, app_role_id),
    constraint fk_user_role_user foreign key (app_user_id) references app_user(app_user_id),
    constraint fk_user_role_role foreign key (app_role_id) references app_role(app_role_id)
);

create table collaborator(
	app_user_id		int not null,
    playlist_id		int not null,
    
    accepted	boolean default 0,
    
    constraint pk_user_playlist primary key (app_user_id, playlist_id),
    constraint fk_user_playlist_user foreign key (app_user_id) references app_user(app_user_id),
    constraint fk_user_playlist_playlist foreign key (playlist_id) references playlist(playlist_id)
);

 insert into app_user (app_user_id, first_name, last_name, username, password_hash, email, disabled) values
		('1', 'Alice', 'Ayers', 'AAyers', '$2a$10$rMhw6y8yy0jd0ZEyx/Hh3ulyySNNoQeskoxjv0BmFfdm/tEmUGRv6', 'aayers@apple.com', 0),
        ('2', 'Bob', 'Bobberson', 'BBobers', '$2a$10$rMhw6y8yy0jd0ZEyx/Hh3ulyySNNoQeskoxjv0BmFfdm/tEmUGRv6', 'bbobberson@bob.bob', 0),
        ('3', 'Clyde', 'Clemens', 'CClems', '$2a$10$rMhw6y8yy0jd0ZEyx/Hh3ulyySNNoQeskoxjv0BmFfdm/tEmUGRv6', 'cclemens@clementine.net', 0),
        ('4', 'Danielle', 'Davison', 'DDavis', '$2a$10$rMhw6y8yy0jd0ZEyx/Hh3ulyySNNoQeskoxjv0BmFfdm/tEmUGRv6', 'ddavison@dmail.web', 0);
        
	insert into app_role (app_role_id, `name`) values
		('1', 'USER'),
        ('2', 'GROUP-ADMIN'),
        ('3', 'APP-ADMIN');
        
	insert into playlist (playlist_id, `name`, `description`, owner_id) values
		('1', 'Jazzy jazz', 'Smooth, classic, and always fresh', '1'),
        ('2', 'Jam rock', 'A fusion of rock and long-winded jam sessions', '2'),
        ('3', 'lo-fi hip-hop', 'music to study to', '3'),
        ('4', 'workout', 'gotta get that pump', '4'),
        ('5', 'Classical', 'Pip pip', '2'),
        ('6', 'Heavy Metal', 'YEAAAAAAAA', '2'),
        ('7', 'R&B Soul', 'Get your body moving', '1'); 
    
    
    insert into user_role (app_user_id, app_role_id) values
		('1', '1'),
        ('1', '2'),
        ('2', '1'),
        ('2', '2'),
        ('3', '1'),
        ('4', '3');

	insert into collaborator (app_user_id, playlist_id, accepted) values
		('1', '1', '1'),
        ('1', '2', '1'),
        ('2', '2', '1'),
        ('2', '1', '0'),
        ('2', '3', '1'),
        ('3', '2', '1'),
        ('2', '4', '0'),
        ('2', '7', '1');