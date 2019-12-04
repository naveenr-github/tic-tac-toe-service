# tic-tac-toe-service
 
Create Game POST Request:

http://localhost:8080/game

Request Body:
`
{
	"name": "naveen",
	"character": "O"
}
`

Response:

`{
    "id": 100,
    "title": "TicTacToe Game",
    "name": "naveen",
    "character": "O",
    "stateCode": "IN_PROGRESS"
}
`

Get Game Details GET Request:

http://localhost:8080/game/100

Response:

`{
    "id": 100,
    "title": "TicTacToe Game",
    "name": "naveen",
    "character": "O",
    "state": {
        "code": "IN_PROGRESS",
        "title": "In progress"
    },
    "lastTurn": {
        "x": 0,
        "y": 0
    },
    "snapshot": [
        [
            " ",
            " ",
            " "
        ],
        [
            " ",
            " ",
            " "
        ],
        [
            " ",
            " ",
            " "
        ]
    ]
}
`

Move Game POST Request:

http://localhost:8080/game/100/move

Request Body:


`{
	"x": "0",
	"y": "0"
}
`

Response Body:

`{
    "code": "IN_PROGRESS",
    "title": "In progress"
}
`
