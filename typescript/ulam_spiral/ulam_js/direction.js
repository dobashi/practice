// 方向
class Direction {
  constructor(name, step) {
    this.name = name;
    this.step = step;
    this.add = (p) => new Position(p.x + step.x, p.y + step.y);
  }
}

const Right = new Direction('Right', {x:1, y:0});
const Up = new Direction('Ujp',  {x:0, y:1});
const Left = new Direction('Left',  {x:-1, y:0});
const Down = new Direction('Down',  {x:0, y:-1});

let directions = [Right, Up, Left, Down];
let nextDirection = direction => {
        console.log(direction)
  let index = directions.findIndex(d => d.name == direction.name);
  let next = index == directions.length - 1 ? 0 : index + 1;
  return directions[next];
};

