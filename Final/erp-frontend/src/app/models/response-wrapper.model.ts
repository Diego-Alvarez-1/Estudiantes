export interface ResponseWrapper<T> {
  data: T;
  message: string;
  status: number;
  errorCode?: string;
  errors?: FieldError[];
}

export interface FieldError {
  field: string;
  code: string;
  message: string;
}

export interface Cliente {
  id?: number;
  nombre: string;
  email: string;
  telefono?: string;
  direccion?: string;
  rut: string;
  activo?: boolean;
  fechaCreacion?: Date;
}

export interface Producto {
  id?: number;
  codigo: string;
  nombre: string;
  descripcion?: string;
  precio: number;
  stockActual?: number;
  stockMinimo?: number;
  activo?: boolean;
  fechaCreacion?: Date;
}

export interface Proveedor {
  id?: number;
  nombre: string;
  email: string;
  telefono?: string;
  direccion?: string;
  rut: string;
  activo?: boolean;
  fechaCreacion?: Date;
}

export interface Venta {
  id?: number;
  numeroFactura?: string;
  fechaVenta?: Date;
  total?: number;
  estado?: EstadoVenta;
  cliente?: Cliente;
  detalles?: DetalleVenta[];
}

export interface DetalleVenta {
  id?: number;
  producto?: Producto;
  cantidad: number;
  precioUnitario?: number;
  subtotal?: number;
}

export interface Compra {
  id?: number;
  numeroOrden?: string;
  fechaCompra?: Date;
  total?: number;
  estado?: EstadoCompra;
  proveedor?: Proveedor;
  detalles?: DetalleCompra[];
}

export interface DetalleCompra {
  id?: number;
  producto?: Producto;
  cantidad: number;
  precioUnitario: number;
  subtotal?: number;
}

export enum EstadoVenta {
  PENDIENTE = 'PENDIENTE',
  PAGADA = 'PAGADA',
  CANCELADA = 'CANCELADA'
}

export enum EstadoCompra {
  PENDIENTE = 'PENDIENTE',
  RECIBIDA = 'RECIBIDA',
  CANCELADA = 'CANCELADA'
}