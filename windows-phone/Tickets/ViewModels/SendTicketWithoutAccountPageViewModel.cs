using Caliburn.Micro;

namespace Tickets.ViewModels
{
    public class SendTicketWithoutAccountPageViewModel : PropertyChangedBase
    {
        private readonly INavigationService _navigationService;

        public SendTicketWithoutAccountPageViewModel(INavigationService navigationService)
        {
            _navigationService = navigationService;
        }

        public void SendTicket()
        {
            _navigationService.For<MyTicketsPageViewModel>().Navigate();
        }
    }
}
