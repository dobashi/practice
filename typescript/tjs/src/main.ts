import { Object3D, FontLoader, Font, MeshBasicMaterial, Mesh, TextGeometry, MeshStandardMaterial, DirectionalLight, WebGLRenderer, Scene, PerspectiveCamera, MeshNormalMaterial, AmbientLight, Vector3, AxesHelper, ShapeGeometry, AnimationAction } from 'three';
import { stage } from './Stage';


window.addEventListener('load', async () => {
    document.body.appendChild(stage.renderer.domElement)
    stage.animate()

    stage.addText('Dobashi', new Vector3(0, 0, 1), 0)
    stage.addText('Lavans', new Vector3(0, 0, 1), 100)
})

const byId = (id) => <HTMLInputElement>document.getElementById(id);
byId("add").onclick = (e) => stage.addText(byId('text').value, new Vector3(1, 0, 1), 200);

console.log("add button event")
console.log(byId("add"))

