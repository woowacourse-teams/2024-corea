import styled from "styled-components";

export const CalendarDropdownContainer = styled.section`
  position: relative;
  width: 130px;
`;

export const CalendarDropdownToggle = styled.input<{ $error: boolean }>`
  cursor: pointer;

  width: 100%;
  padding: 0.6rem 1.1rem;

  font: ${({ theme }) => theme.TEXT.semiSmall};
  text-align: center;
  letter-spacing: 0.2rem;

  border: 1px solid ${(props) => (props.$error ? props.theme.COLOR.error : props.theme.COLOR.grey1)};
  border-radius: 6px;
  outline-color: ${({ theme }) => theme.COLOR.black};
`;
