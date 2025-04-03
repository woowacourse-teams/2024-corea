import styled from "styled-components";

export const AlarmIconWrapper = styled.div<{ $isMain: boolean }>`
  position: relative;

  display: flex;
  align-items: center;
  justify-content: center;

  padding: 1rem;

  border-radius: 50%;

  &:hover {
    background-color: ${({ theme, $isMain }) => ($isMain ? theme.COLOR.white : theme.COLOR.grey0)};
  }
`;

export const Count = styled.div`
  position: absolute;
  top: 40%;
  left: 70%;
  transform: translateX(-50%);

  display: flex;
  align-items: center;
  justify-content: center;

  width: 1.9rem;
  height: 1.9rem;

  font: ${({ theme }) => theme.TEXT.xSmall};
  color: ${({ theme }) => theme.COLOR.white};
  text-align: center;

  background-color: ${({ theme }) => theme.COLOR.grey4};
  border-radius: 50%;
`;
