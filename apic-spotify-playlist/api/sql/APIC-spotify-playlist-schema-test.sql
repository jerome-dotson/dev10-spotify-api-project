drop database if exists APIc_spotify_playlist_test;
create database APIc_spotify_playlist_test;
use APIc_spotify_playlist_test;

create table app_user(
	app_user_id 	int primary key auto_increment,
    first_name		varchar(100) not null,
    last_name		varchar(100) not null,
    username		varchar(100) not null unique,
    password_hash	char(100) not null,
    email			varchar(200) not null unique,
    isDeleted		boolean default 0
);

create table app_role(
	role_id		int primary key auto_increment,
    `name`		varchar(50) not null
);

create table playlist(
	playlist_id		int primary key auto_increment,
    `name`			varchar(100) not null,
    `description`		varchar(1000),
    
    app_user_id			int not null,
    constraint fk_playlist_user foreign key (app_user_id) references app_user(app_user_id)
);

create table track(
	track_id		int primary key auto_increment,
    `name`			varchar(250) not null,
    duration_ms		bigint not null
);

create table artist(
	artist_id		int primary key auto_increment,
    `name`			varchar(250) not null
);

create table tag(
	tag_id 			int primary key auto_increment,
    content			varchar(250) not null,
    
    app_user_id			int not null,
    constraint fk_tag_user foreign key (app_user_id) references app_user(app_user_id)
);

create table image(
	image_id		int primary key auto_increment,
    url				varchar(250) not null,
    height			int not null,
    width			int not null
);

create table user_role(
	app_user_id		int not null,
    role_id			int not null,
    
    constraint pk_user_role primary key (app_user_id, role_id),
    constraint fk_user_role_user foreign key (app_user_id) references app_user(app_user_id),
    constraint fk_user_role_role foreign key (role_id) references app_role(role_id)
);

create table user_playlist(
	app_user_id		int not null,
    playlist_id		int not null,
    
    accepted	boolean default 0,
    
    constraint pk_user_playlist primary key (app_user_id, playlist_id),
    constraint fk_user_playlist_user foreign key (app_user_id) references app_user(app_user_id),
    constraint fk_user_playlist_playlist foreign key (playlist_id) references playlist(playlist_id)
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

create table tag_playlist(
	tag_id			int not null,
    playlist_id		int not null,
    
    constraint pk_tag_playlist primary key (tag_id, playlist_id),
    constraint fk_tag_playlist_tag foreign key (tag_id) references tag(tag_id),
    constraint fk_tag_playlist_playlist foreign key (playlist_id) references playlist(playlist_id)
);

create table image_playlist(
	image_id		int not null,
    playlist_id		int not null,
    
    constraint pk_image_playlist primary key (image_id, playlist_id),
    constraint fk_image_playlist_image foreign key (image_id) references image(image_id),
    constraint fk_image_playlist_playlist foreign key (playlist_id) references playlist(playlist_id)
);

create table track_artist(
	track_id		int not null,
    artist_id		int not null,
    
    constraint pk_track_artist primary key (track_id, artist_id),
    constraint fk_track_artist_track foreign key (track_id) references track(track_id),
    constraint fk_track_artist_artist foreign key (artist_id) references artist(artist_id)
);

delimiter //
create procedure set_good_known_state()
begin

	delete from track_artist;
    delete from image_playlist;
    delete from tag_playlist;
    delete from track_playlist;
    delete from user_playlist;
    delete from user_role;
    delete from image;
    alter table image auto_increment = 1;
    delete from tag;
    alter table tag auto_increment = 1;
    delete from artist;
    alter table artist auto_increment = 1;
    delete from track;
    alter table track auto_increment = 1;
    delete from playlist;
    alter table playlist auto_increment = 1;
    delete from app_role;
    alter table app_role auto_increment = 1;
    delete from app_user;
    alter table app_user auto_increment = 1;
    
    insert into app_user (app_user_id, first_name, last_name, username, passhash, email, isDeleted) values
		('1', 'Alice', 'Ayers', 'AAyers', '12345asdfg', 'aayers@apple.com', 0),
        ('2', 'Bob', 'Bobberson', 'BBobers', '23456sdfgh', 'bbobberson@bob.bob', 0),
        ('3', 'Clyde', 'Clemens', 'CClems', '34567dfghj', 'cclemens@clementine.net', 0),
        ('4', 'Danielle', 'Davison', 'DDavis', '45678fghjk', 'ddavison@dmail.web', 0);
        
	insert into app_role (role_id, `name`) values
		('1', 'USER'),
        ('2', 'GROUP-ADMIN'),
        ('3', 'APP-ADMIN');
        
	insert into playlist (playlist_id, `name`, `description`, app_user_id) values
		('1', 'Jazzy jazz', 'Smooth, classic, and always fresh', '1'),
        ('2', 'Jam rock', 'A fusion of rock and long-winded jam sessions', '2');
    
    insert into track (track_id, `name`, duration_ms) values
		('1', 'Bird Food', '331000'),
        ('2', 'Self-Portrait in Three Colours', '187000'),
        ('3', 'So What', '561000'),
        ('4', 'Peaceful', '1083000'),
        ('5', 'Layla', '422000'),
        ('6', 'In the Air Tonight', '336000'),
        ('7', 'Friend of the Devil', '205000'),
        ('8', 'Smooth (feat. Rob Thomas)', '296000'),
        ('9', 'Hungersite', '427000'),
        ('10', "Don't Lose Site", '208000');
    
    insert into artist (artist_id, `name`) values
		('1', 'Ornet Coleman'),
        ('2', 'Charles Mingus'),
        ('3', 'Miles Davis'),
        ('4', 'Eric Clapton'),
        ('5', 'Phil Collins'),
        ('6', 'Grateful Dead'),
        ('7', 'Santana'),
        ('8', 'Goose'),
        ('9', 'Lawrence');
    
    insert into tag (tag_id, content, app_user_id) values
		('1', 'Jazz', '1'),
        ('2', 'Good vibes', '2'),
        ('3', 'Rockin', '2');
    
    insert into image (image_id, url, height, width) values
		('1', 'https://placekitten.com/300/300', '300', '300'),
		('2', 'https://placekitten.com/300/300', '300', '300');
    
    insert into user_role (app_user_id, role_id) values
		('1', '1'),
        ('1', '2'),
        ('2', '1'),
        ('2', '2'),
        ('3', '1'),
        ('4', '3');

	insert into user_playlist (app_user_id, playlist_id, accepted) values
		('1', '1', '1'),
        ('1', '2', '1'),
        ('2', '2', '1'),
        ('2', '1', '0'),
        ('3', '2', '1');
        
	insert into track_playlist (track_id, playlist_id, app_user_id) values
		('1', '1', '1'),
        ('2', '1', '1'),
        ('3', '1', '1'),
        ('4', '1', '1'),
        ('5', '2', '1'),
        ('6', '2', '2'),
        ('7', '2', '2'),
        ('8', '2', '3'),
        ('9', '2', '3'),
        ('10', '2', '1'),
        ('10', '1', '1');
        
	insert into tag_playlist (tag_id, playlist_id) values
		('1', '1'),
        ('2', '2'),
        ('3', '2');
        
	insert into image_playlist (image_id, playlist_id) values
		('1', '1'),
        ('2', '2');
        
	insert into track_artist (track_id, artist_id) values
		('1', '1'),
        ('2', '2'),
        ('3', '3'),
        ('4', '3'),
        ('5', '4'),
        ('6', '5'),
        ('7', '6'),
        ('8', '7'),
        ('9', '8'),
        ('10', '9');
        
end //
delimiter ;
