import styled from "styled-components";

export const Layout = styled.div`
  scroll-behavior: smooth;
  scroll-snap-type: y mandatory;

  position: relative;

  overflow-y: scroll;
  display: flex;
  flex-direction: column;
  align-items: center;

  height: 100vh;

  background-color: ${({ theme }) => theme.COLOR.grey0};
`;

export const ContentLayout = styled.div`
  scroll-snap-align: start;

  position: relative;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 1200px;
  min-height: 100vh;

  background-color: ${({ theme }) => theme.COLOR.primary2};
  border: 1px solid ${({ theme }) => theme.COLOR.black};
`;
