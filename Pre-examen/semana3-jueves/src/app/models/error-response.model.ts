export default interface ErrorResponseModel {
  message: string;
  status: number;
  errorCode: string | null;
}
