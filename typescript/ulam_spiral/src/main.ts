import { Vector3 } from 'three';
import { TextColor, stage } from './Stage';
import { directions, ground, primes, PositionDirection } from "./Ulam";

const vector = (p, z) => new Vector3(p.x, p.y, z);

window.addEventListener('load', async () => {
  console.log('load')
  setTimeout(init, 1000)
})

const init = () => {
  document.body.appendChild(stage.renderer.domElement)
  stage.animate()
  spiral()
}

const spiral = () => {
  console.log('spiral')
  let current: PositionDirection = directions.first
  const scale = 140
  let z = 0;
  ground.push(current.position)
  stage.addText("1", vector(current.position, z), scale, Color.start)

  // from 2 to 1000
  for (let i = 2; i < 80; i++) {
    let next = ground.next(current);
    // drawLine(current.position, next.position)
    ground.push(next.position)
    const color = primes.isPrime(i) ? Color.prime : Color.normal
    stage.addText(str(i), vector(next.position, z += 0.05), scale, color)
    current = next
  }
}
const str = (i: number) => new Number(i).toString()

const Color = {
  start: { fg: 0x00CC33, bg: 0x009821 } as TextColor,
  normal: { fg: 0x336699, bg: 0x003399 } as TextColor,
  prime: { fg: 0xAA2211, bg: 0x993322 } as TextColor
}

//   white = 0xFFFFFF,
//   offwhite = 0xFAFDF0,
//   purple = 0xCE217E,
//   blue = 0x336699,


