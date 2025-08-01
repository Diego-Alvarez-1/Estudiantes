import {CommonModule} from '@angular/common';
import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {AulasService} from '../service/aulas.service';
import ErrorResponseModel from '../models/error-response.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-aulas',
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './aulas.component.html',
  standalone: true,
  styleUrl: './aulas.component.css'
})
export class AulasComponent implements OnInit {

  public formAula: FormGroup;
  aulas: any;

  constructor(private readonly fb: FormBuilder, private readonly aulasService: AulasService) {
    this.formAula = this.fb.group({
      nombre: ['', Validators.required],
      codigo: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    console.log('*****')
    this.listar();
  }

  registrar() {
    if (!this.formAula.valid) return false;
    this.aulasService.guardarAulas(this.formAula.getRawValue()).subscribe({
      next: (response: any) => {
        console.log(response);
        this.formAula.reset();
        this.listar();
      },
      error: (e: any) => {
        const errorResponse = e.error as ErrorResponseModel;
        console.info("MESSAGE: " + errorResponse.message);
        console.info("CODE: " + errorResponse.errorCode);
        Swal.fire(
          {
            icon: 'warning',
            title: errorResponse.errorCode !== null ? errorResponse.errorCode: 'Codigo por Defecto',
            text: errorResponse.message !== null ? errorResponse.message: 'Mensaje por Defecto'
          }
        );
        this.formAula.controls['codigo'].setValue('');
      }
    })
    return true;
  }

  listar() {
    this.aulasService.obtenerAulas().subscribe({
      next: (response) => {
        console.log(response);
        this.aulas = response;
      },
      error: (e: any) => {
        console.error(e);
      }
    })
  }


}
