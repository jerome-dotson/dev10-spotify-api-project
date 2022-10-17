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