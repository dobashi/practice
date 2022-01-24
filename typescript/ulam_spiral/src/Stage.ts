import { Object3D, FontLoader, Font, MeshBasicMaterial, Mesh, TextGeometry, MeshStandardMaterial, DirectionalLight, WebGLRenderer, Scene, PerspectiveCamera, MeshNormalMaterial, AmbientLight, Vector3, AxesHelper, ShapeGeometry, AnimationAction } from 'three';
import { OrbitControls } from './OrbitControls.js'
// import { OrbitControls } from 'three-orbit-controls'

export class TextColor {
  fg: number
  bg: number
}
enum Color {
  offwhite = 0xFAFDF0,
}

class Stage {
  renderer: WebGLRenderer
  scene: Scene
  camera: PerspectiveCamera
  controls: any
  constructor() {
    this.renderer = renderer()
    this.scene = scene()
    this.camera = new PerspectiveCamera(45, 800 / 600, 1, 10000)
    this.camera.position.set(0, 0, 2000)
    this.controls = new OrbitControls(this.camera, this.renderer.domElement)
  }
  add = (g: Object3D) => this.scene.add(g)
  animate = (): void => {
    requestAnimationFrame(this.animate)
    this.controls.update()
    this.renderer.render(this.scene, this.camera)
  }
  addText = async (text: string, position: Vector3, distance, color) => {
    const t = await createText(text, color)
    t.translateOnAxis(position, distance)
    this.scene.add(t)
    this.animate()
  }
}
const renderer = () => {
  const r = new WebGLRenderer()
  r.setSize(1024, 768)
  r.setClearColor(Color.offwhite)
  r.domElement.id = 'canvas'
  return r
}
const scene = () => {
  const s = new Scene()
  s.add(ambientLight())
  s.add(directionLight())
  helpers().forEach(e => { s.add(e) })
  return s
}
const ambientLight = () => new AmbientLight(0xffffff)
const directionLight = () => {
  const light = new DirectionalLight(0xffffff)
  light.position.set(1, 1, 100)
  return light
}

const helpers = (): Object3D[] => {
  return [new AxesHelper(1000)]
}

// const range = (from: number, to: number) => ([...Array(to - from)].map((_, i) => (from + i)))
class Fonts {
  private loader: FontLoader
  private map: Map<string, Font>
  constructor() {
    this.map = new Map()
    this.loader = new FontLoader()
    this.loader.load('fonts/helvetiker_regular.typeface.json', (font) => this.map.set('helvetiker', font))
  }
  public get(name): Promise<Font> {
    console.log('Fonts.get()')
    return new Promise((resolve, reject) => {
      const font = this.map.get(name)
      if (this.map.has(name)) resolve(font)
      else setTimeout(() => this.get(name), 500)
    })
  }
}

const fonts = new Fonts()
const createText = (text: string, color: TextColor) => fonts.get('helvetiker').then(font => {
  const textGeometry = new TextGeometry(text, {
    font: font,
  })
  var materials = [ // facecolor, sidecolor
    new MeshStandardMaterial({ color: color.fg }),
    new MeshStandardMaterial({ color: color.bg }),
  ]
  return new Mesh(textGeometry, materials);
})

export const stage = new Stage()
