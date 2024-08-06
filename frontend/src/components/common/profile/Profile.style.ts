import styled from "styled-components";

export const ProfileContainer = styled.button<{ $size: number | undefined }>`
  cursor: pointer;

  overflow: hidden;

  width: ${({ $size }) => ($size ? $size : 32)}px;
  height: ${({ $size }) => ($size ? $size : 32)}px;

  object-fit: contain;
  border-radius: 50%;
`;

export const ProfileImg = styled.img<{ $size: number | undefined }>`
  width: ${({ $size }) => ($size ? $size - 2 : 30)}px;
  height: ${({ $size }) => ($size ? $size - 2 : 30)}px;
`;
