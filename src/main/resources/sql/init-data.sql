insert into state (st_id, st_code, st_title) values
(1, 'IN_PROGRESS', 'In progress'),
(2, 'X_WON', 'X won'),
(3, 'O_WON', 'O won'),
(4, 'DRAW', 'Draw');

insert into location (lc_id, lc_x, lc_y) values
 (1, 0, 1),
 (2, 1, 1),
 (3, 1, 2);

insert into snapshot (sn_id, sn_last_turn_id, sn_dump) values
(1, null, '         '),
(2, 1,    ' o x o x '),
(3, 2,    'o   x    '),
(4, 3,    'ox ox  x ');



insert into game (gm_id, gm_state_id, gm_title, gm_snapshot_id) values
(1, 1, 'Empty game',      1),
(2, 1, 'The second one',  2),
(3, 1, 'The third',       3),
(4, 2, 'Winner game',     4);

insert into game_to_location (gl_game_id, gl_location_id) values
(2, 1),

(3, 2),

(4, 3);