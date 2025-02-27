import styled from "styled-components";

export const Layout = styled.div`
  display: flex;
  flex-direction: column;
`;

export const RoomCreateButton = styled.button`
  position: fixed;
  right: 50px;
  bottom: 50px;

  display: flex;
  align-items: center;
  justify-content: flex-start;

  width: 50px;
  height: 50px;
  padding: 0 10px;

  font: ${({ theme }) => theme.TEXT.large_bold};
  color: ${({ theme }) => theme.COLOR.white};

  background-color: ${({ theme }) => theme.COLOR.grey4};
  border-radius: 50px;
  box-shadow: 2px 2px 2px rgb(0 0 0 / 25%);

  transition: width 0.3s ease-in-out;

  svg {
    position: absolute;
    left: 10px;
  }

  span {
    overflow: hidden;
    display: inline-block;

    width: 0;

    white-space: nowrap;

    opacity: 0;

    transition:
      width 0.3s ease,
      opacity 0.3s ease;
  }

  &:hover {
    width: 120px;
  }

  &:hover span {
    width: auto;
    margin-left: 30px;
    opacity: 1;
  }

  @media screen and (width >= 1200px) {
    right: calc((100% - 1200px) / 2 + 50px);
  }
`;
