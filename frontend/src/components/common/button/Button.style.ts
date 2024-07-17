import styled from "styled-components";

export const Button = styled.button<{ color: "primary2" | "secondary" | "grey4" }>`
  cursor: pointer;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 6rem;
  height: 2rem;
  padding: 0.2rem;

  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: white;
  text-align: center;

  background: ${({ color, theme }) => theme.COLOR[color]};
  border-radius: 0.5rem;

  &:hover {
    opacity: 0.6;
  }
`;
