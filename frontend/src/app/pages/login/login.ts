import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login implements OnInit{
  form: FormGroup;
  constructor(private readonly fb:FormBuilder){
    this.form = fb.group({
      usuario:[''],
      contraseña:['']
    });
  }
  ngOnInit():void {
    console.log("->", this.form.getRawValue())
  }
   iniciarSesion():void {
    console.log("->", this.form.getRawValue())
  }

}
